import pandas as pd
import math as m

def arrayfixer(str_arr):
    
    if (str_arr == '[]'):
        return 0
    elif(type(str_arr) == None):
        return 0
    elif(isinstance(str_arr,int)):
        return str_arr
    elif(isinstance(str_arr,float)):
        return str_arr
    if len(str_arr)>1:
        cleaned_list = [token.replace('[', '').replace(']', '').replace(',','') for token in str_arr]
        new_array = []  
        i =  0
        while i < len(cleaned_list):
            i += 1
            f_token = ''
            if i<len(cleaned_list):
                while cleaned_list[i]:
                    if(cleaned_list[i] == ' ' or cleaned_list[i] == ''):
                        break
                    else:
                        f_token = f_token + cleaned_list[i]
                        i += 1
                    
            if not f_token=='':
                new_array.append(f_token)         
        float_array = [float(i) for i in new_array if i]

        print(float_array)
        return float_array
    else:
        return 0

def eucclidean_norm(arr):
    norm = 0
    if(isinstance(arr,int)):
        return arr
    if(isinstance(arr,float)):
        return arr
    elif len(arr) > 1:
        for i in arr:
           norm += m.sqrt(i*i)
    else: 
        return arr        
    return norm

def cell_cleaner(cell):
    #cell.replace('[', '').replace(']', '').replace(',','')

    return str(cell)

def weighted_sum(arr):
    norm = 0
    if(isinstance(arr,int)):
        return arr
    if(isinstance(arr,float)):
        return arr
    elif len(arr) > 1:
        for i in arr:
           weight = i
           norm += m.sqrt((i*i)/weight)
    else: 
        return arr        
    return norm

path = r'C:\Users\theos\OneDrive - Novotek AB\Desktop\Studier\CERN 10HP\FCCExoticHiggsTrial\sample_B_bak.csv'
BAKdt = pd.read_csv(path, dtype={'ID': int}).dropna().reset_index(drop=True)
BAKdt['Signal'] = 0

path = r'C:\Users\theos\OneDrive - Novotek AB\Desktop\Studier\CERN 10HP\FCCExoticHiggsTrial\sample_C_targ.csv'
TARdt = pd.read_csv(path, dtype={'ID': int}).dropna().reset_index(drop=True)
TARdt['Signal'] = 1

#path = r'C:\Users\theos\OneDrive - Novotek AB\Desktop\Studier\CERN 10HP\FCCExoticHiggsTrial\sample_A.csv'
#mixeddt = pd.read_csv(path, dtype={'ID': int}).dropna().reset_index(drop=True)

#print(BAKdt.shape[0:])
print(TARdt.shape[0:])
#print(mixeddt.shape[0:])



BAKdt.drop(BAKdt[BAKdt['n_seltracks_DVs'] == 0].index, inplace = True)
TARdt.drop(TARdt[TARdt['n_seltracks_DVs'] == 0].index, inplace = True)
# mixeddt.drop(mixeddt[mixeddt['n_seltracks_DVs'] == 0].index, inplace = True)

#df1_processed = BAKdt.applymap(arrayfixer)
df2_processed = TARdt.applymap(arrayfixer)
# df3_processed = mixeddt.applymap(arrayfixer)



#df1_processed = df1_processed.applymap(eucclidean_norm)
#df1_processed = df1_processed.applymap(cell_cleaner)

df2_processed = df2_processed.applymap(eucclidean_norm)
df2_processed = df2_processed.applymap(cell_cleaner)

#df3_processed = df3_processed.applymap(eucclidean_norm)
#df3_processed = df3_processed.applymap(cell_cleaner)

#columns_to_drop =[]

# for header in df1_processed:
#     first_run = df1_processed
#     for ind in df1_processed.index:
#         if type(df1_processed.loc[ind, header]) == str:
#             #value = arrayfixer(df1_processed.loc[ind, header])
#             if first_run:
#                 columns_to_drop.append(header)
#                 for j in range(len(value)):
#                     df1_processed[header + '_v' + str(j)] = 0 
#                 first_run = False

#             # Assign values to the new columns for the current row `ind`
#             for y, i in enumerate(value):
#                 df1_processed.loc[ind, header + '_v' + str(y)] = i


# for header in df2_processed:
#     first_run = True
#     for ind in df2_processed.index:
#         if type(df2_processed.loc[ind, header]) == str:
#             value = arrayfixer(df2_processed.loc[ind, header])          
#             if first_run:
#                 for j in range(len(value)):
#                     df2_processed[header + '_v' + str(j)] = 0 
#                 first_run = False
    
#             for y, i in enumerate(value):
#                 df2_processed.loc[ind, header + '_v' + str(y)] = i  

#outfilenamedf1 = r'C:\Users\theos\OneDrive - Novotek AB\Desktop\Studier\CERN 10HP\FCCExoticHiggsTrial\sample_B_bak_filt.csv'
outfilenamedf2 = r'C:\Users\theos\OneDrive - Novotek AB\Desktop\Studier\CERN 10HP\FCCExoticHiggsTrial\sample_C_targ_filt.csv'
#outfilenamedf3 = r'C:\Users\theos\OneDrive - Novotek AB\Desktop\Studier\CERN 10HP\FCCExoticHiggsTrial\sample_A_filt.csv'

#df1_processed.to_csv(outfilenamedf1, index=False)
df2_processed.to_csv(outfilenamedf2, index=False)
#df3_processed.to_csv(outfilenamedf3, index=False)
print("File saved successfully!")