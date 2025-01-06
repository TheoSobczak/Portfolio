# Load the data
# url = 'data/biopsy.csv'
import plotly.graph_objects as go
import plotly.express as px
import plotly.graph_objects as go
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from statistics import mean 

import sklearn.preprocessing as skl_pre
import torchvision as torch
import sklearn.model_selection as skl_ms
from sklearn.preprocessing import StandardScaler
import sklearn.neighbors as skl_nb
from sklearn.model_selection import KFold
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler
from sklearn.neighbors import KNeighborsClassifier

import seaborn as sns

sc = StandardScaler()
path = r'C:\Users\theos\OneDrive - Novotek AB\Desktop\Studier\CERN 10HP\Filtered Euc_Norm\BAKSignalDVFilteredEuc.csv'
BAKdt = pd.read_csv(path, dtype={'ID': int}).dropna().reset_index(drop=True)
BAKdt['Signal'] = 0

path = r'C:\Users\theos\OneDrive - Novotek AB\Desktop\Studier\CERN 10HP\Filtered Euc_Norm\TARGSignalDVFilteredEuc.csv'
TARdt = pd.read_csv(path, dtype={'ID': int}).dropna().reset_index(drop=True)
TARdt['Signal'] = 1

# n_RecoedPrimaryTracks, n_trks_merged_DVs,n_trks_seltracks_DVs,n_seltracks_DVs
    
merged_df = pd.concat([BAKdt, TARdt], ignore_index=True)

for column in merged_df.columns: 
    merged_df[column] = merged_df[column]  / merged_df[column].abs().max() 
merged_df.head()

#X = merged_df[['n_RecoedPrimaryTracks','n_trks_merged_DVs','n_trks_seltracks_DVs','n_seltracks_DVs']]
X = merged_df[['n_RecoedPrimaryTracks','n_trks_merged_DVs']]
y = merged_df['Signal']

#np.squeeze(y).shape

xnum = np.asarray(X)
ynum = np.asarray(y)
mesh_size = .02
margin = 1

# We will use the iris data, which is included in px
df = px.data.iris()
df_train, df_test = train_test_split(df, test_size=0.25, random_state=0)
X_train = df_train[['sepal_length', 'sepal_width']]
y_train = df_train.species_id

# Create a mesh grid on which we will run our model
l_min, l_max = df.sepal_length.min() - margin, df.sepal_length.max() + margin
w_min, w_max = df.sepal_width.min() - margin, df.sepal_width.max() + margin
lrange = np.arange(l_min, l_max, mesh_size)
wrange = np.arange(w_min, w_max, mesh_size)
ll, ww = np.meshgrid(lrange, wrange)

# Create classifier, run predictions on grid
clf = KNeighborsClassifier(15, weights='distance')
clf.fit(X_train, y_train)
Z = clf.predict(np.c_[ll.ravel(), ww.ravel()])
Z = Z.reshape(ll.shape)
proba = clf.predict_proba(np.c_[ll.ravel(), ww.ravel()])
proba = proba.reshape(ll.shape + (3,))

# Compute the confidence, which is the difference
diff = proba.max(axis=-1) - (proba.sum(axis=-1) - proba.max(axis=-1))

fig = px.scatter(
    df_test, x='sepal_length', y='sepal_width',
    symbol='Signals',
    symbol_map={
        'Target_signal': 'square-dot',
        'Background_signal': 'circle-dot'
        },
)
fig.update_traces(
    marker_size=12, marker_line_width=1.5,
    marker_color="lightyellow"
)
fig.add_trace(
    go.Heatmap(
        x=lrange,
        y=wrange,
        z=diff,
        opacity=0.25,
        customdata=proba,
        colorscale='RdBu',
        hovertemplate=(
            'sepal length: %{x} <br>'
            'sepal width: %{y} <br>'
            'p(setosa): %{customdata[0]:.3f}<br>'
            'p(versicolor): %{customdata[1]:.3f}<br>'
            'p(virginica): %{customdata[2]:.3f}<extra></extra>'
        )
    )
)
fig.update_layout(
    legend_orientation='h',
    title=dict(text='Prediction Confidence on Test Split')
)
fig.show()