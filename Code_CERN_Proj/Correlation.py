# Load the data
# url = 'data/biopsy.csv'
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from statistics import mean 

import sklearn.preprocessing as skl_pre
import torchvision as torch


from sklearn.model_selection import KFold
from sklearn.model_selection import cross_val_score
from sklearn.preprocessing import StandardScaler

import seaborn as sns



path = r'C:\Users\theos\OneDrive - Novotek AB\Desktop\Studier\CERN 10HP\FCCExoticHiggsTrial\sample_B_bak_filt.csv'
BAKdt = pd.read_csv(path, dtype={'ID': int}).dropna().reset_index(drop=True)


path = r'C:\Users\theos\OneDrive - Novotek AB\Desktop\Studier\CERN 10HP\FCCExoticHiggsTrial\sample_C_targ_filt.csv'
TARdt = pd.read_csv(path, dtype={'ID': int}).dropna().reset_index(drop=True)


merged_df = pd.concat([BAKdt, TARdt], ignore_index=True)
print(merged_df.info())

#for column in merged_df.columns: 
#    merged_df[column] = merged_df[column]  / merged_df[column].abs().max() 


#X = merged_df[['n_RecoedPrimaryTracks','n_trks_merged_DVs','n_trks_seltracks_DVs','n_seltracks_DVs']]

corr = merged_df.corr()
print(corr.loc['Signal'].sort_values())

   

# plt.show()

#print (h_day)

#print(data.corr())
                    


# plt.plot(X_train, y_train,marker='o', color = 'red')
# plt.plot(X_test, y_test,marker='o', color = 'blue')
#print(mean(scores))
# plt.xlabel('Cross Validation')
# plt.ylabel('Accuracy')
# i = np.argmax(scores)
# mean_s = mean(scores)
# y_min = scores[i]



