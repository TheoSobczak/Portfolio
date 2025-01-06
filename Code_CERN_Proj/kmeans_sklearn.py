
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from statistics import mean 
from sklearn.cluster import KMeans

import sklearn.neighbors as skl_nb
import sklearn.model_selection as skl_ms

from sklearn.model_selection import KFold
from sklearn.model_selection import cross_val_score
from sklearn.preprocessing import StandardScaler
from sklearn import metrics
import seaborn as sns

def purity_score(y_true, y_pred):
    # compute contingency matrix (also called confusion matrix)
    contingency_matrix = metrics.cluster.contingency_matrix(y_true, y_pred)
    # return purity
    return np.sum(np.amax(contingency_matrix, axis=0)) / np.sum(contingency_matrix)

sc = StandardScaler()
path = r'C:\Users\theos\OneDrive - Novotek AB\Desktop\Studier\CERN 10HP\Filtered Euc_Norm\BAKSignalDVFilteredEuc.csv'
BAKdt = pd.read_csv(path, dtype={'ID': int}).dropna().reset_index(drop=True)
BAKdt['Signal'] = 0

path = r'C:\Users\theos\OneDrive - Novotek AB\Desktop\Studier\CERN 10HP\Filtered Euc_Norm\TARGSignalDVFilteredEuc.csv'
TARdt = pd.read_csv(path, dtype={'ID': int}).dropna().reset_index(drop=True)
TARdt['Signal'] = 1

# path = r'C:\Users\theos\OneDrive - Novotek AB\Desktop\Studier\CERN 10HP\FCCExoticHiggsTrial\sample_B_bak_filt.csv'
# BAKdt = pd.read_csv(path, dtype={'ID': int}).dropna().reset_index(drop=True)
# BAKdt['Signal'] = 0

# path = r'C:\Users\theos\OneDrive - Novotek AB\Desktop\Studier\CERN 10HP\FCCExoticHiggsTrial\sample_C_targ_filt.csv'
# TARdt = pd.read_csv(path, dtype={'ID': int}).dropna().reset_index(drop=True)
# TARdt['Signal'] = 1

cleaned_bak = []
# n_RecoedPrimaryTracks, n_trks_merged_DVs,n_trks_seltracks_DVs,n_seltracks_DVs
    
merged_df = pd.concat([BAKdt, TARdt], ignore_index=True)

# for column in merged_df.columns: 
#     merged_df[column] = merged_df[column]  / merged_df[column].abs().max() 
# merged_df.head()

X = merged_df[['n_RecoedPrimaryTracks','n_trks_seltracks_DVs','n_seltracks_DVs']]
y = merged_df['Signal']
scaled_data = sc.fit_transform(merged_df[['n_RecoedPrimaryTracks','n_trks_seltracks_DVs','n_seltracks_DVs']])
# inertias = []

# for i in range(1,11):
#     kmeans = KMeans(n_clusters=i)
#     kmeans.fit(merged_df)
#     inertias.append(kmeans.inertia_)

# plt.plot(range(1,11), inertias, marker='o')
# plt.title('Elbow method')
# plt.xlabel('Number of clusters')
# plt.ylabel('Inertia')
# plt.show()

xnum = np.asarray(scaled_data)
ynum = np.asarray(y)


kmeans = KMeans(n_clusters=2, n_init='auto')
kmeans.fit(xnum)
y_kmeans = kmeans.predict(xnum)
print(y_kmeans.shape[0:])
# plt.scatter(xnum[:, 0], xnum[:, 3], c=y_kmeans, s=50, cmap='viridis')
#label = kmeans.fit_predict(xnum)
print(purity_score(ynum,y_kmeans))
plt.scatter(ynum,y_kmeans)
plt.show()
# centers = kmeans.cluster_centers_
# plt.scatter(centers[:, 0], centers[:, 3], c='black', s=200, alpha=0.5)
