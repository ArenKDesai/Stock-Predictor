import os
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import sklearn.model_selection
from sklearn.preprocessing import MinMaxScaler
from keras.models import Sequential
from keras.layers import Dense, LSTM, Dropout
from keras.models import load_model
import yfinance as yf
from pandas_datareader import data as pdr
from datetime import datetime
from bs4 import BeautifulSoup as bs
import requests
os.chdir("C://Users//arenk//Documents//stock_predictor")

def get_stock_data():
    url = "https://mergr.com/public-information-technology-companies"
    page = requests.get(url)
    soup = bs(page.content, "html.parser")
    stocks = soup.findAll("span", {"class": "label label-sm label-primary"})
    print(stocks)

def train(stock_name, epochs, batch_size, verbose):
    df = pdr.get_data_yahoo('AAPL', start = '2012-01-01', end=datetime.now())
    data = df.filter([verbose])
    dataset = data.values
    training_data_len = int(np.ceil(len(dataset) * 0.95))

    scaler = MinMaxScaler(feature_range=(0,1))
    scaled_data = scaler.fit_transform(dataset)

    train_data = scaled_data[0:int(training_data_len),:]
    x_train = []
    y_train = []

    for i in range(60, len(train_data)):
        x_train.append(train_data[i-60:i, 0])
        y_train.append(train_data[i,0])
        if i<=61:
            print(x_train)
            print(y_train)
            print()

    x_train, y_train = np.array(x_train), np.array(y_train)
    x_train = np.reshape(x_train, (x_train.shape[0], x_train.shape[1], 1))

    model = Sequential()
    model.add(LSTM(128, return_sequences=True, input_shape = (x_train.shape[1], 1)))
    model.add(LSTM(64, return_sequences=False))
    model.add(Dense(25))
    model.add(Dense(1))

    model.compile(optimizer='adam', loss='mean_squared_error')
    model.fit(x_train, y_train, batch_size = batch_size, epochs = epochs)

if (__name__ == "__main__"):
    get_stock_data()



# dataset = pd.read_csv('IndexProcessed.csv')
# training_set, testing_set = sklearn.model_selection.train_test_split(dataset, test_size=0.2, random_state=0)
# training_set = training_set.iloc[:, 2:3].values
# scaler = MinMaxScaler(feature_range=(0, 1))
# scaled_training_set = scaler.fit_transform(training_set)
# X_train = []
# y_train = []
# for i in range(60, 1258):
#     X_train.append(scaled_training_set[i-60:i, 0])
#     y_train.append(scaled_training_set[i, 0])
# X_train, y_train = np.array(X_train), np.array(y_train)
# X_train = np.reshape(X_train, (X_train.shape[0], X_train.shape[1], 1))
# regressor = load_model('Stock_Predictor.keras')

# actual_stock_price = testing_set.iloc[0:20, 2:3].values
# dataset_total = pd.concat((dataset['Open'], testing_set['Open']), axis=0)
# inputs = dataset_total[len(dataset_total)-len(testing_set)-60:].values
# inputs = inputs.reshape(-1, 1)
# inputs = scaler.transform(inputs)
# X_test = []
# for i in range(60, 80):
#     X_test.append(inputs[i-60:i, 0])
# X_test = np.array(X_test)
# X_test = np.reshape(X_test, (X_test.shape[0], X_test.shape[1], 1))
# predicted_stock_price = regressor.predict(X_test)
# predicted_stock_price = scaler.inverse_transform(predicted_stock_price)
# plt.plot(actual_stock_price, color='red', label='Actual Stock Price')
# plt.plot(predicted_stock_price, color='blue', label='Predicted Stock Price')
# plt.title('Stock Price Prediction')
# plt.xlabel('Time')
# plt.ylabel('Stock Price')
# plt.legend()
# plt.show()