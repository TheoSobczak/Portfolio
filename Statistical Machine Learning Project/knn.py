# Load the data
# url = 'data/biopsy.csv'
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from statistics import mean 

import sklearn.preprocessing as skl_pre
import sklearn.linear_model as skl_lm
import sklearn.discriminant_analysis as skl_da
import sklearn.neighbors as skl_nb
import sklearn.model_selection as skl_ms
from matplotlib.colors import ListedColormap

from sklearn.model_selection import KFold
from sklearn.model_selection import cross_val_score
from sklearn.preprocessing import StandardScaler

import seaborn as sns



sc = StandardScaler()
url = r"https://uppsala.instructure.com/courses/80540/files/5835593/download?download_frd=1"
data = pd.read_csv(url, dtype={'ID': str}).dropna().reset_index(drop=True)
data['increase_stock'] = np.where(data['increase_stock'] == 'low_bike_demand', 0, 1)

data.drop(columns=['increase_stock'])
data.head()
X = data[['hour_of_day','summertime','temp','humidity']]

X1 = data[['hour_of_day','summertime','temp','humidity',
           'dew','weekday']]

X2 = data[['hour_of_day','summertime','temp','humidity',
           'dew','weekday','visibility','windspeed']]

y = data['increase_stock']
np.squeeze(y).shape

xnum = np.asarray(X)
ynum = np.asarray(y)
x1num = np.asarray(X1)
ynum = np.asarray(y)
x2num = np.asarray(X2)
ynum = np.asarray(y)

scores = []
scores1 = []
scores2 = []
k_list= []
k1_list= []
k2_list= []

# X_train, X_test, y_train, y_test = skl_ms.train_test_split(xnum, ynum,test_size=0.7, random_state=np.random.randint(1,50))
# knn = skl_nb.KNeighborsClassifier(n_neighbors = 17)
# knn.fit(X_train, y_train)


# test_preds = knn.predict(X_test)
# print(test_preds)

# plt.show()                          


for k in range (1,80):
    X_train, X_test, y_train, y_test = skl_ms.train_test_split(xnum, ynum,test_size=0.7, random_state=np.random.randint(1,50))
    knn = skl_nb.KNeighborsClassifier(n_neighbors = k)
    knn.fit(X_train, y_train)
    k_list.append(k)
    scores.append(1 - knn.score(X_test, y_test))
print("4 variables lowest score: " + str(np.min(scores)))

plt.plot(k_list,scores, color = 'red', label = '4 dimensions')

for k in range (1,80):
    X_train, X_test, y_train, y_test = skl_ms.train_test_split(x1num, ynum,test_size=0.7, random_state=np.random.randint(1,50))
    knn = skl_nb.KNeighborsClassifier(n_neighbors = k)
    knn.fit(X_train, y_train)
    k1_list.append(k)
    scores1.append(1 - knn.score(X_test, y_test))
print("6 variables lowest score: " + str(np.min(scores1)))    

plt.plot(k1_list,scores1, color = 'blue', label = '6 dimensions')


for k in range (1,80):
    X_train, X_test, y_train, y_test = skl_ms.train_test_split(x2num, ynum,test_size=0.7, random_state=np.random.randint(1,50))
    knn = skl_nb.KNeighborsClassifier(n_neighbors = k)
    knn.fit(X_train, y_train)
    k2_list.append(k)
    scores2.append(1 - knn.score(X_test, y_test))    
print("8 variables lowest score: " + str(np.min(scores2)))  

plt.plot(k2_list,scores2, color = 'black', label = '8 dimensions')

plt.title('K for dimension 4,6,8 and range 80')
plt.xlabel('K values')
plt.ylabel('Error')

i = np.argmin(scores)
y_min = scores[i]
print(y_min)
print(i)
plt.text(k_list[i],y_min, k_list[i])
plt.plot(k_list[i], y_min, marker='o',color= 'red', label = '4d lowest' )

i1 = np.argmin(scores1)
y_min1 = scores1[i1]
print(y_min1)
print(k1_list[i1])
plt.text(k1_list[i1],y_min1, k1_list[i1])
plt.plot(k1_list[i1], y_min1, marker='o',color= 'blue' ,label = '6d lowest'  )

i2 = np.argmin(scores2)
y_min2 = scores2[i2]
print(y_min2)
print(k1_list[i2])
plt.text(k2_list[i2],y_min2, k2_list[i2])
plt.plot(k2_list[i2], y_min2, marker='o',color= 'black' , label = '8d lowest' )
plt.legend(loc = 'upper right')

plt.show()   

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



