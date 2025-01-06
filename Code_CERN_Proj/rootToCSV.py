import numpy as np
import pandas as pd
import uproot
import awkward as ak

fileIn = './FCCExoticHiggsStudentProject/ntuple_wzp6_ee_eeH_Hbb_ecm240.root'
treename = 'events'
data1 = uproot.open(fileIn)[treename]

fileIn = './FCCExoticHiggsStudentProject/ntuple_exoticHiggs_scalar_ms40GeV_sine-6.root'
data2 = uproot.open(fileIn)[treename]

fields = ['n_tracks', 'n_RecoedPrimaryTracks', 'n_seltracks_DVs',
       'n_trks_seltracks_DVs', 'invMass_seltracks_DVs',
       'DV_evt_seltracks_chi2', 'DV_evt_seltracks_normchi2',
       'Reco_seltracks_DVs_Lxy', 'Reco_seltracks_DVs_Lxyz',
       'n_seltracks_trackCut_DVs', 'n_trks_seltracks_trackCut_DVs',
       'invMass_seltracks_trackCut_DVs', 'DV_evt_seltracks_trackCut_normchi2',
       'Reco_seltracks_trackCut_DVs_Lxyz', 'n_seltracks_fullVertexSel_DVs',
       'n_trks_seltracks_fullVertexSel_DVs',
       'invMass_seltracks_fullVertexSel_DVs',
       'DV_evt_seltracks_fullVertexSel_normchi2',
       'Reco_seltracks_fullVertexSel_DVs_Lxyz',
       'n_seltracks_fullVertexSel_lowDist_DVs',
       'n_trks_seltracks_fullVertexSel_lowDist_DVs',
       'invMass_seltracks_fullVertexSel_lowDist_DVs',
       'DV_evt_seltracks_fullVertexSel_lowDist_normchi2',
       'Reco_seltracks_fullVertexSel_lowDist_DVs_Lxyz', 'n_merged_DVs',
       'n_trks_merged_DVs', 'invMass_merged_DVs', 'merged_DVs_chi2',
       'merged_DVs_normchi2', 'Reco_DVs_merged_Lxy', 'Reco_DVs_merged_Lxyz',
       'n_merged_trackCut_DVs', 'n_trks_merged_trackCut_DVs',
       'invMass_merged_trackCut_DVs', 'DV_evt_merged_trackCut_normchi2',
       'Reco_merged_trackCut_DVs_Lxyz', 'n_merged_fullVertexSel_DVs',
       'n_trks_merged_fullVertexSel_DVs', 'invMass_merged_fullVertexSel_DVs',
       'DV_evt_merged_fullVertexSel_normchi2',
       'Reco_merged_fullVertexSel_DVs_Lxyz',
       'n_merged_fullVertexSel_lowDist_DVs',
       'n_trks_merged_fullVertexSel_lowDist_DVs',
       'invMass_merged_fullVertexSel_lowDist_DVs',
       'DV_evt_merged_fullVertexSel_lowDist_normchi2',
       'Reco_merged_fullVertexSel_lowDist_DVs_Lxyz', 'n_RecoElectrons',
       'n_electrons_sel_iso', 'RecoElectron_e', 'RecoElectron_p',
       'RecoElectron_pt', 'RecoElectron_px', 'RecoElectron_py',
       'RecoElectron_pz', 'RecoElectron_charge', 'Reco_ee_invMass',
       'n_RecoMuons', 'n_muons_sel_iso', 'RecoMuon_e', 'RecoMuon_p',
       'RecoMuon_pt', 'RecoMuon_px', 'RecoMuon_py', 'RecoMuon_pz',
       'RecoMuon_charge', 'Reco_mumu_invMass']

DVfields = ['n_tracks', 'n_RecoedPrimaryTracks', 'n_seltracks_DVs',
       'n_trks_seltracks_DVs', 'invMass_seltracks_DVs',
       'DV_evt_seltracks_chi2', 'DV_evt_seltracks_normchi2',
       'Reco_seltracks_DVs_Lxy', 'Reco_seltracks_DVs_Lxyz',
       'n_seltracks_trackCut_DVs', 'n_trks_seltracks_trackCut_DVs',
       'invMass_seltracks_trackCut_DVs', 'DV_evt_seltracks_trackCut_normchi2',
       'Reco_seltracks_trackCut_DVs_Lxyz', 'n_seltracks_fullVertexSel_DVs',
       'n_trks_seltracks_fullVertexSel_DVs',
       'invMass_seltracks_fullVertexSel_DVs',
       'DV_evt_seltracks_fullVertexSel_normchi2',
       'Reco_seltracks_fullVertexSel_DVs_Lxyz',
       'n_seltracks_fullVertexSel_lowDist_DVs',
       'n_trks_seltracks_fullVertexSel_lowDist_DVs',
       'invMass_seltracks_fullVertexSel_lowDist_DVs',
       'DV_evt_seltracks_fullVertexSel_lowDist_normchi2',
       'Reco_seltracks_fullVertexSel_lowDist_DVs_Lxyz', 'n_merged_DVs',
       'n_trks_merged_DVs', 'invMass_merged_DVs', 'merged_DVs_chi2'
       ]

#filtered_names = ["n_seltracks_DVs",'invMass_seltracks_DVs','DV_evt_seltracks_normchi2','Reco_seltracks_DVs_Lxy',]

#bad columns == 'DV_evt_seltracks_normchi2','Reco_seltracks_DVs_Lxy', 'n_electrons_sel_iso'

valid_fields = [field for field in DVfields if field in data1.keys()]
dfObj1 = data1.arrays(valid_fields,library="pd")
#dfObj2 = data2.arrays(valid_fields,library="pd")



print(dfObj1.info())
dfObj1 = dfObj1.dropna(how='all', axis=1) 
dfObj1 = dfObj1.dropna(how='all', axis=0) 

print(f"After dropna: {dfObj1.shape[0]} rows, {dfObj1.shape[1]} columns")
print(dfObj1.info())


for column in dfObj1.columns:
    if dfObj1[column].iloc[0] is (None):
            dfObj1 = dfObj1[dfObj1[column].notna()].reset_index(drop=True)
    if isinstance(dfObj1[column].iloc[0], (list, ak.Array)):
        dfObj1[column] = dfObj1[column].apply(lambda x: ak.to_list(x) if isinstance(x, ak.Array) else x)
       
        

print("done")
#for column in dfObj2.columns:
#    if isinstance(dfObj2[column].iloc[0], ak.Array):
#        dfObj2[column] = ak.to_list(dfObj2[column])
#        dfObj2 = dfObj2.explode(column).reset_index(drop=True)        

# Drop rows where all values are NaN


#dfObj2 = dfObj2.dropna(how='all', axis=1) 
#dfObj2 = dfObj2.dropna(how='all', axis=0)  # Drop empty rows

outfilenamedf1 = r'C:\Users\theos\OneDrive - Novotek AB\Desktop\Studier\CERN 10HP\BAKSignalDV.csv'
#outfilenamedf2 = r'C:\Users\theos\OneDrive - Novotek AB\Desktop\Studier\CERN 10HP\TARGSignalDV.csv'

print(dfObj1.shape[0:])
print(dfObj1.info())
print(dfObj1.head())

chunk_size = 10000  
try:
    for i, chunk in enumerate(range(0, len(dfObj1), chunk_size)):
        chunk_df = dfObj1.iloc[chunk:chunk + chunk_size]
        chunk_df.to_csv(outfilenamedf1, mode='a', index=False, header=(i == 0))  
    #dfObj1.to_csv(outfilenamedf1, index=False)
    #dfObj2.to_csv(outfilenamedf2, index=False)
    print("File saved successfully!")
except Exception as e:
    print(f"An error occurred: {e}")



first_run = bool
columns_to_drop = []


# mixeddt = pd.DataFrame(mixeddt)

# for header in mixeddt:
#     first_run = True
#     print('Running through header ' + header)
#     for ind in mixeddt.index:
#         if isinstance(mixeddt.loc[ind, header], str):
#             value = arrayfixer(mixeddt.loc[ind, header])
#             # Ensure `value` is iterable before iterating over it
#             if first_run:
#                 for j in range(len(value)):
#                     mixeddt[header + ' v' + str(j)] = 0  # Create columns only once
#                 first_run = False

#             # Assign values to the new columns for the current row `ind`
#             for y, i in enumerate(value):
#                 mixeddt.loc[ind, header + ' v' + str(y)] = i
                
# try:
#     mixeddt.to_csv(r'C:\Users\theos\OneDrive - Novotek AB\Desktop\Studier\CERN 10HP\mixedDT.csv', index=False)
#     print("File saved successfully!")
# except Exception as e:
#     print(f"An error occurred: {e}")

#flat_tree1 = uproot.open(r"./FCCExoticHiggsStudentProject/ntuple_wzp6_ee_eeH_Hbb_ecm240.root:events")
#flat_tree2 = uproot.open(r"./FCCExoticHiggsStudentProject/ntuple_exoticHiggs_scalar_ms40GeV_sine-6.root:events")

#df1 = flat_tree1.arrays(library="pd")
# df2 = flat_tree2.arrays(library="pd")

# df1.to_numpy()
# np.savetxt('./FCCExoticHiggsStudentProject/BAKSignal.csv',df1)

#Look at n_RecoedPrimaryTracks
# n_trks_seltracks_DVs, invMass_seltracks_DVs
# All DV good (Y)
# 'n_trks_merged_DVs', 'invMass_merged_DVs', 'merged_DVs_chi2',
#       'merged_DVs_normchi2', 'Reco_DVs_merged_Lxy', 'Reco_DVs_merged_Lxyz',
#       'n_merged_trackCut_DVs', 'n_trks_merged_trackCut_DVs',
#       'invMass_merged_trackCut_DVs', 'DV_evt_merged_trackCut_normchi2',
#       'Reco_merged_trackCut_DVs_Lxyz',

# Try 'n_tracks', 'n_RecoedPrimaryTracks', 'n_seltracks_DVs'
# Plot BAK and Target.


        
        #If time look at the last ones muons.