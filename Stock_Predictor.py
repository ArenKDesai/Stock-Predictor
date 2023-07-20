import os
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import sklearn.model_selection
from sklearn.preprocessing import MinMaxScaler
from keras.models import Sequential
from keras.layers import Dense, LSTM, Dropout

os.chdir("C://Users//arenk//Documents//stock_predictor")

dataset = pd.read_csv('IndexProcessed.csv')
training_set, testing_set = sklearn.model_selection.train_test_split(dataset, test_size=0.2, random_state=0)
training_set = training_set.iloc[:, 2:3].values
scaler = MinMaxScaler(feature_range=(0, 1))
scaled_training_set = scaler.fit_transform(training_set)
X_train = []
y_train = []
for i in range(60, 1258):
    X_train.append(scaled_training_set[i-60:i, 0])
    y_train.append(scaled_training_set[i, 0])
X_train, y_train = np.array(X_train), np.array(y_train)
X_train = np.reshape(X_train, (X_train.shape[0], X_train.shape[1], 1))
