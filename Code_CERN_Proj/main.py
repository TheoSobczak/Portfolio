
import uproot
from six import print_
import numpy as np
import pandas as pd




path = r'C:\Users\theos\OneDrive - Novotek AB\Desktop\Studier\CERN 10HP\Filtered Euc_Norm\BAKSignalDVFilteredEuc.csv'
BAKdt = pd.read_csv(path, dtype={'ID': int}).dropna().reset_index(drop=True)
BAKdt['Signal'] = 0

path = r'C:\Users\theos\OneDrive - Novotek AB\Desktop\Studier\CERN 10HP\Filtered Euc_Norm\TARGSignalDVFilteredEuc.csv'
TARdt = pd.read_csv(path, dtype={'ID': int}).dropna().reset_index(drop=True)
TARdt['Signal'] = 1


#print(BAKdt['invMass_seltracks_DVs v0'].iloc[0])
#print(TARdt['invMass_seltracks_DVs v0'].iloc[0])
cleaned_bak = []
#print(BAKdt.columns.intersection(TARdt.columns))      

merged_df = pd.concat([BAKdt, TARdt], ignore_index=True)

# X = np.asarray(df1)
# Y = np.asarray(df2)


# # get the number of features 
# num_features = X.shape[1]
# # Initialize weights randomly
# weights = np.random.randn(num_features)
# # learning rate
# learning_rate=0.01
# # no of iterations 
# epochs=100
# # Add a bias term (constant feature) to X
# X = np.concatenate([X, np.ones((X.shape[0], 1))], axis=1)

# class NeuralNet(object):

#     def __init__(self, num_features):
#         # Initialize weights randomly
#         self.weights = np.random.randn(num_features)

#     def forward_pass(self, X):
#         # Implement the forward pass
#         prediction = self.predict(X)
#         return prediction

#     def error_function(self, y, prediction):
#         # Implement the error function
#         error = y - prediction
#         return error

#     def backward_prop(self, error, learning_rate, X):
#         # Implement the backward propagation
#         self.weights += learning_rate * error * X
#         return self.weights

#     def train(self, X, y, learning_rate=0.01, epochs=100):
#         # Train the neural network using LMS algorithm
#         for epoch in range(epochs):
#           for i in range(X.shape[0]):
#             prediction = self.forward_pass(X[i])
#             error = self.error_function(y[i], prediction)
#             weights = self.backward_prop(error, learning_rate, X[i])
#         return weights

#     def predict(self, X):
#         prediction = np.dot(X, self.weights)
#         return prediction

# # Generate some synthetic training data
# np.random.seed(42)  # Using a different seed for uniqueness
# # Different true weights for generating y
# true_weights = np.array([4, -2]) 

# # Add a bias term (constant feature) to X
# X = np.concatenate([X, np.ones((X.shape[0], 1))], axis=1)

# # Neural Network
# neural_net = NeuralNet(X.shape[1])
# learned_weights = neural_net.train(X, Y)

# print("True Weights:", true_weights)
# print("Learned Weights:", learned_weights)



# mixeddt = { 'n_seltracks_DVs' : [1,2,3],
#             'Signal' : [0,1,0],
#             'invMass_seltracks_DVs': ['[1.22, 1.33, 1.44]','[1.22, 1.33, 1.44]','[1.22, 1.33, 1.44]'],
#             'DV_evt_seltracks_normchi2' : ['[2.22, 2.33, 2.44]','[2.22, 2.33, 2.44]','[2.22, 2.33, 2.44]'],
#             'Reco_seltracks_DVs_Lxy' : ['[3.22, 3.33, 3.44]','[3.22, 3.33, 3.44]','[3.22, 3.33, 3.44]']}
