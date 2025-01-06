# Load the data
# url = 'data/biopsy.csv'
import plotly.graph_objects as go
from sklearn.datasets import make_moons
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from statistics import mean 

import sklearn.preprocessing as skl_pre
import torchvision as torch
import sklearn.model_selection as skl_ms
from sklearn.preprocessing import StandardScaler
from sklearn.preprocessing import MinMaxScaler
import sklearn.neighbors as skl_nb
from sklearn.model_selection import KFold
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler
from sklearn.neighbors import KNeighborsClassifier

import seaborn as sns


path = r'C:\Users\theos\OneDrive - Novotek AB\Desktop\Studier\CERN 10HP\Filtered Euc_Norm\BAKSignalDVFilteredEuc.csv'
BAKdt = pd.read_csv(path, dtype={'ID': int}).dropna().reset_index(drop=True)
BAKdt['Signal'] = 0


path = r'C:\Users\theos\OneDrive - Novotek AB\Desktop\Studier\CERN 10HP\Filtered Euc_Norm\TARGSignalDVFilteredEuc.csv'
TARdt = pd.read_csv(path, dtype={'ID': int}).dropna().reset_index(drop=True)
TARdt['Signal'] = 1

#path = r'C:\Users\theos\OneDrive - Novotek AB\Desktop\Studier\CERN 10HP\FCCExoticHiggsTrial\sample_B_bak_filt.csv'
#BAKdt_one = pd.read_csv(path, dtype={'ID': int}).dropna().reset_index(drop=True)
#BAKdt_one = ['n_RecoedPrimaryTracks','n_trks_seltracks_DVs','n_seltracks_DVs', 'Signal']
#path = r'C:\Users\theos\OneDrive - Novotek AB\Desktop\Studier\CERN 10HP\FCCExoticHiggsTrial\sample_A_filt.csv'
#BAKdt_two = pd.read_csv(path, dtype={'ID': int}).dropna().reset_index(drop=True)
#BAKdt_two['Signal'] = 0
#BAKdt_two = ['n_RecoedPrimaryTracks','n_trks_seltracks_DVs','n_seltracks_DVs', 'Signal']

#erged_bak = pd.concat([BAKdt_one, BAKdt_two], ignore_index=True)

#path = r'C:\Users\theos\OneDrive - Novotek AB\Desktop\Studier\CERN 10HP\FCCExoticHiggsTrial\sample_C_targ_filt.csv'
#TARdt = pd.read_csv(path, dtype={'ID': int}).dropna().reset_index(drop=True)
#TARdt = ['n_RecoedPrimaryTracks','n_trks_seltracks_DVs','n_seltracks_DVs', 'Signal']

# n_RecoedPrimaryTracks, n_trks_merged_DVs,n_trks_seltracks_DVs,n_seltracks_DVs



# for column in merged_df.columns: 
#     merged_df[column] = merged_df[column]  / merged_df[column].abs().max() 
# merged_df.head()

#X = merged_df[['n_RecoedPrimaryTracks','n_trks_merged_DVs','n_trks_seltracks_DVs','n_seltracks_DVs']]
# Xbak = BAKdt[['n_RecoedPrimaryTracks','n_trks_seltracks_DVs','n_seltracks_DVs']]
# Xtar = TARdt[['n_RecoedPrimaryTracks','n_trks_seltracks_DVs','n_seltracks_DVs']]
# datapoints = [i for i in range(9926)]

# mean_arr_bak = [Xbak['n_RecoedPrimaryTracks'].mean,Xbak['n_trks_seltracks_DVs'].mean,Xbak['n_seltracks_DVs'].mean]
# mean_arr_tar = [Xtar['n_RecoedPrimaryTracks'].mean,Xtar['n_trks_seltracks_DVs'].mean,Xtar['n_seltracks_DVs'].mean]
# print(mean_arr_bak)
# print("next arr")
# print(mean_arr_tar)

sc = StandardScaler()
merged_df = pd.concat([BAKdt, TARdt], ignore_index=True)

# for column in merged_df.columns: 
#     merged_df[column] = merged_df[column]  / merged_df[column].abs().max()
X = merged_df[['n_RecoedPrimaryTracks','n_trks_seltracks_DVs']]
y = merged_df['Signal']
scaled_data = sc.fit_transform(X)
#scaled_df = pd.DataFrame(scaled_data, columns=scaled_data.columns)
#print(scaled_df.head())

y = np.squeeze(y)

xnum = np.asarray(scaled_data)


# plt.plot(datapoints,Xtar['n_RecoedPrimaryTracks'][:9926], label = 'Target Signal')
# plt.plot(datapoints,Xbak['n_RecoedPrimaryTracks'][:9926], label = 'Background Signal')

# plt.plot(datapoints,Xtar['n_trks_seltracks_DVs'][:9926], label = 'Target Signal')
# plt.plot(datapoints,Xbak['n_trks_seltracks_DVs'][:9926], label = 'Background Signal')

# plt.plot(datapoints,Xtar['n_seltracks_DVs'][:9926], label = 'Target Signal',alpha = 0.7)
# plt.plot(datapoints,Xbak['n_seltracks_DVs'][:9926], label = 'Background Signal', alpha = 0.7)

# plt.title('Target and Background Signals n_seltracks_DVs Trial')
# plt.xlabel('Data Points')
# plt.ylabel('Magnitude')
# plt.legend()
# plt.show()

mesh_size = .02
margin = 0.25

#Load and split data

X_train, X_test, y_train, y_test = train_test_split(
    xnum, y.astype(str), test_size=0.5, random_state=0)

# Create a mesh grid on which we will run our model
x_min, x_max = xnum[:, 0].min() - margin, xnum[:, 0].max() + margin
y_min, y_max = xnum[:, 1].min() - margin, xnum[:, 1].max() + margin
xrange = np.arange(x_min, x_max, mesh_size)
yrange = np.arange(y_min, y_max, mesh_size)
xx, yy = np.meshgrid(xrange, yrange)

# Create classifier, run predictions on grid
clf = KNeighborsClassifier(3, weights='uniform')
clf.fit(xnum, y)
Z = clf.predict_proba(np.c_[xx.ravel(), yy.ravel()])[:, 1]
Z = Z.reshape(xx.shape)

trace_specs = [
    [X_train, y_train, '0', 'Train', 'square'],
    [X_train, y_train, '1', 'Train', 'circle'],
    [X_test, y_test, '0', 'Test', 'square-dot'],
    [X_test, y_test, '1', 'Test', 'circle-dot']
]

fig = go.Figure(data=[
    go.Scatter(
        x=xnum[y==label, 0], y=xnum[y==label, 1],
        name=f'{split} Split, Label {label}',
        mode='markers', marker_symbol=marker
    )
    for xnum, y, label, split, marker in trace_specs
])
fig.update_traces(
    marker_size=12, marker_line_width=1.5,
    marker_color="lightyellow"
)

fig.add_trace(
    go.Contour(
        x=xrange,
        y=yrange,
        z=Z,
        showscale=False,
        colorscale='RdBu',
        opacity=0.4,
        name='Score',
        hoverinfo='skip'
    )
)
fig.show()





# scores_v.append(1 - knn.score(X_train, y_train))
# scores_t.append(1 - knn.score(X_test, y_test))


# for k in range (1,80):
#     X_train, X_test, y_train, y_test = skl_ms.train_test_split(xnum, ynum,test_size=0.7, random_state=np.random.randint(1,50))
#     knn = skl_nb.KNeighborsClassifier(n_neighbors = k)
#     knn.fit(X_train, y_train)
#     k_list.append(k)
#     scores.append(1 - knn.score(X_test, y_test))

# cv = KFold(n_splits=8, random_state=62, shuffle=True)
# X_train, X_test, y_train, y_test = skl_ms.train_test_split(xnum, ynum,test_size=0.92, random_state=60)
# knn = skl_nb.KNeighborsClassifier(n_neighbors = 17)
# knn.fit(X_train, y_train)
# holdout_score = (knn.score(X_train, y_train))

#scores = cross_val_score(knn, X_test, y_test, scoring='accuracy', cv=cv, n_jobs=-1)


# plt.plot(X_train, y_train,marker='o', color = 'red')
# plt.plot(X_test, y_test,marker='o', color = 'blue')
#print(mean(scores))
# plt.xlabel('Cross Validation')
# plt.ylabel('Accuracy')
# i = np.argmax(scores)
# mean_s = mean(scores)
# y_min = scores[i]
# print(y_min)
# plt.text(i,y_min, y_min)
# plt.plot(i, y_min, marker='o', )
# plt.text(1, holdout_score, 'holdout score')
# plt.plot(1, holdout_score, marker='o', )
# plt.legend(['mean = ' + str(mean_s)], loc = 'upper right') 

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



