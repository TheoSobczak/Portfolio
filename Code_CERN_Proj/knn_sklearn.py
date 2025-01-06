# Load the data
# url = 'data/biopsy.csv'
import pandas as pd
import numpy as np
import pandas as pd
import numpy as np
import sklearn.neighbors as skl_nb
import sklearn.model_selection as skl_ms
from sklearn.preprocessing import StandardScaler
from sklearn.model_selection import KFold
from matplotlib import pyplot as plt




sc = StandardScaler()
# path = r'C:\Users\theos\OneDrive - Novotek AB\Desktop\Studier\CERN 10HP\FCCExoticHiggsTrial\sample_B_bak_filt.csv'
# BAKdt = pd.read_csv(path, dtype={'ID': int}).dropna().reset_index(drop=True)


# path = r'C:\Users\theos\OneDrive - Novotek AB\Desktop\Studier\CERN 10HP\FCCExoticHiggsTrial\sample_C_targ_filt.csv'
# TARdt = pd.read_csv(path, dtype={'ID': int}).dropna().reset_index(drop=True)

sc = StandardScaler()
path = r'C:\Users\theos\OneDrive - Novotek AB\Desktop\Studier\CERN 10HP\Filtered Euc_Norm\BAKSignalDVFilteredEuc.csv'
BAKdt = pd.read_csv(path, dtype={'ID': int}).dropna().reset_index(drop=True)
BAKdt['Signal'] = 0

path = r'C:\Users\theos\OneDrive - Novotek AB\Desktop\Studier\CERN 10HP\Filtered Euc_Norm\TARGSignalDVFilteredEuc.csv'
TARdt = pd.read_csv(path, dtype={'ID': int}).dropna().reset_index(drop=True)
TARdt['Signal'] = 1 

# n_RecoedPrimaryTracks, n_trks_merged_DVs,n_trks_seltracks_DVs,n_seltracks_DVs
    
merged_df = pd.concat([BAKdt, TARdt], ignore_index=True)

# for column in merged_df.columns: 
#     merged_df[column] = merged_df[column]  / merged_df[column].abs().max() 
# merged_df.head()


scaled_data = sc.fit_transform(merged_df[['n_RecoedPrimaryTracks','n_trks_seltracks_DVs','n_seltracks_DVs']])
#X = scaled_data[['n_RecoedPrimaryTracks','n_trks_seltracks_DVs','n_seltracks_DVs']]
X = scaled_data
y = merged_df['Signal']

np.squeeze(y).shape

xnum = np.asarray(X)
ynum = np.asarray(y)


scores_t = []
scores_v = []
k_list= []

k=3


#create a new KNN model
X_train, X_test, y_train, y_test = skl_ms.train_test_split(xnum, ynum,test_size=0.6, random_state=np.random.randint(1,50))
for i in range(3):
    i = i+3
    knn = skl_nb.KNeighborsClassifier(n_neighbors = i+1)
    knn.fit(X_train, y_train)
   

    scores_t.append(knn.score(X_train, y_train))
    scores_v.append(knn.score(X_test, y_test))

print(scores_t)
print(scores_v)

# error_rate = []
# # Will take some time
# for i in range(1,40):
 
#  knn = skl_nb.KNeighborsClassifier(n_neighbors=i)
#  knn.fit(X_train,y_train)
#  pred_i = knn.predict(X_test)
#  error_rate.append(np.mean(pred_i != y_test))

# plt.figure(figsize=(10,6))
# plt.plot(range(1,40),error_rate,color='blue', linestyle='dashed', marker='o',
#  markerfacecolor='red', markersize=10)
# plt.title('Error Rate vs. K Value')
# plt.xlabel('K')
# plt.ylabel('Error Rate')
# plt.show()
# plt.plot(k_list,scores_v, color = 'red', label = 'Validation')
# plt.plot(k_list,scores_t, color = 'blue', label = 'Training')
# plt.title('K = 3 for dimension 4')
# plt.xlabel('K values')
# plt.ylabel('Error')

# i = np.argmin(scores_v)
# y_min = scores_v[i]
# print(mean(scores_v))
# print(y_min)
# print(i)
# plt.text(k_list[i],y_min, k_list[i])
# plt.plot(k_list[i], y_min, marker='o',color= 'red', label = 'Validation lowest' )

# i1 = np.argmin(scores_t)
# y_min1 = scores_t[i1]
# print(mean(scores_t))
# print(y_min1)
# print(i1)
# plt.text(k_list[i1],y_min1, k_list[i1])
# plt.plot(k_list[i1], y_min1, marker='o',color= 'blue', label = 'Training lowest' )






# # Define a range of k values to test
# k_values = [1, 3, 5, 7, 9, 11]

# # Create subplots for each k value
# fig, axes = plt.subplots(1, len(k_values), figsize=(15, 3))

# # Train and visualize the models with varying k values
# for i, k in enumerate(k_values):
#     # Create KNN model
#     knn = KNeighborsClassifier(n_neighbors=k)

#     # Train the model
#     knn.fit(X_train, y_train)

#     # Make predictions
#     y_pred = knn.predict(X_test)

#     # Calculate accuracy
#     accuracy = accuracy_score(y_test, y_pred)

#     # Plot decision boundary
#     h = 0.02  # Step size in the mesh
#     x_min, x_max = X[:, 0].min() - 1, X[:, 0].max() + 1
#     y_min, y_max = X[:, 1].min() - 1, X[:, 1].max() + 1
#     xx, yy = np.meshgrid(np.arange(x_min, x_max, h),
#                          np.arange(y_min, y_max, h))
#     Z = knn.predict(np.c_[xx.ravel(), yy.ravel()])
#     Z = Z.reshape(xx.shape)

#     # Plot the decision boundary
#     axes[i].contourf(xx, yy, Z, cmap=plt.cm.Paired, alpha=0.8)

#     # Plot the training points
#     axes[i].scatter(X_train[:, 0], X_train[:, 1], c=y_train,
#                     edgecolors='k', cmap=plt.cm.Paired)

#     # Plot the testing points
#     axes[i].scatter(X_test[:, 0], X_test[:, 1], c=y_test,
#                     marker='x', edgecolors='k', cmap=plt.cm.Paired)

#     # Set plot labels and title
#     axes[i].set_title(f'k={k}, Accuracy={accuracy:.2f}')
#     axes[i].set_xlabel('Feature 1')
#     axes[i].set_ylabel('Feature 2')

# plt.show()

# for k in range (1,50):
#     X_train, X_test, y_train, y_test = skl_ms.train_test_split(xnum, ynum,test_size=0.7, random_state=np.random.randint(1,50))
#     knn = skl_nb.KNeighborsClassifier(n_neighbors = k)
#     knn.fit(X_train, y_train)
#     k_list.append(k)
#     scores.append(1 - knn.score(X_test, y_test))
# print("4 variables lowest score: " + str(np.min(scores)))

# plt.plot(k_list,scores, color = 'red', label = '4 dimensions')

# for k in range (1,50):
#     X_train, X_test, y_train, y_test = skl_ms.train_test_split(x1num, ynum,test_size=0.7, random_state=np.random.randint(1,50))
#     knn = skl_nb.KNeighborsClassifier(n_neighbors = k)
#     knn.fit(X_train, y_train)
#     k1_list.append(k)
#     scores1.append(1 - knn.score(X_test, y_test))
# print("6 variables lowest score: " + str(np.min(scores1)))    

# plt.plot(k1_list,scores1, color = 'blue', label = '6 dimensions')

# plt.title('K for dimension 4,6 and range 50')
# plt.xlabel('K values')
# plt.ylabel('Error')

# i = np.argmin(scores)
# y_min = scores[i]
# print(y_min)
# print(i)
# plt.text(k_list[i],y_min, k_list[i])
# plt.plot(k_list[i], y_min, marker='o',color= 'red', label = '4d lowest' )

# i1 = np.argmin(scores1)
# y_min1 = scores1[i1]
# print(y_min1)
# print(k1_list[i1])
# plt.text(k1_list[i1],y_min1, k1_list[i1])
# plt.plot(k1_list[i1], y_min1, marker='o',color= 'blue' ,label = '6d lowest'  )


# plt.show()   

# cv = KFold(n_splits=8, random_state=62, shuffle=True)
# X_train, X_test, y_train, y_test = skl_ms.train_test_split(xnum, ynum,test_size=0.92, random_state=60)
# knn = skl_nb.KNeighborsClassifier(n_neighbors = 17)
# knn.fit(X_train, y_train)
# holdout_score = (knn.score(X_train, y_train))

#scores = cross_val_score(knn, X_test, y_test, scoring='accuracy', cv=cv, n_jobs=-1)




# scores = []
# scores_m = []
# k_list = []
# for k in range(1, 60):
#     scores = []
#     test_s = 0.05
#     for _ in range(10):
#         X_train, X_test, y_train, y_test = skl_ms.train_test_split(xnum, ynum,test_size=test_s, random_state=np.random.randint(1,50))
#         knn = skl_nb.KNeighborsClassifier(n_neighbors = k)
#         knn.fit(X_train, y_train)
#         scores.append(1 - knn.score(X_test, y_test))
#         test_s += 0.05
#     k_list.append(k)   
#     scores_m.append(mean(scores))
#     k += 2
# plt.plot(k_list, scores_m)



