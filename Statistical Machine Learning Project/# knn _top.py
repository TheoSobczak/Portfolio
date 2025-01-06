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
from sklearn.model_selection import GridSearchCV

from sklearn.model_selection import KFold
from sklearn.model_selection import cross_val_score
from sklearn.preprocessing import StandardScaler

import seaborn as sns



sc = StandardScaler()
url = r"https://uppsala.instructure.com/courses/80540/files/5835593/download?download_frd=1"
data = pd.read_csv(url, dtype={'ID': str}).dropna().reset_index(drop=True)
data['increase_stock'] = np.where(data['increase_stock'] == 'low_bike_demand', 0, 1)

data.head()
X = data[['hour_of_day','summertime','temp','humidity']]

y = data['increase_stock']
np.squeeze(y).shape

xnum = np.asarray(X)
ynum = np.asarray(y)


scores_t = []
scores_v = []
k_list= []

k=19

#create a new KNN model
X_train, X_test, y_train, y_test = skl_ms.train_test_split(xnum, ynum,test_size=0.7, random_state=np.random.randint(1,50))
knn = skl_nb.KNeighborsClassifier(n_neighbors = k)
knn.fit(X_train, y_train)
print(knn.predict(X_test))

    # scores_t.append(1 - knn_cv.score(X_train, y_train))
    # scores_v.append(1 - knn_cv.score(X_test, y_test))

# plt.plot(k_list,scores_v, color = 'red', label = 'Validation')
# plt.plot(k_list,scores_t, color = 'blue', label = 'Training')
# plt.title('K = 34 for dimension 4')
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




