# Databricks notebook source
def generateFiles(delim, filepath, const, spdf):
    import time
    date = time.strftime("%m%d%Y")

    import pandas as pd
    from pyspark.sql.functions import concat, col, lit,trim
    
    tempStorage = "/dbfs/temp/pid/ajr_temp"
  
    for i in range(len(delim)):    
        dbutils.fs.rm(tempStorage, recurse = True)
        df=spdf[i].select(concat(lit(const[i]+delim[i]),col("con_upc_no"),lit(delim[i]),col("primary_code"),lit("!"),col("recap_code"),lit("!"),col("subdep_code"),lit("!"),col("commodity_code"),lit("!"),col("subcommodity_code"),lit(delim[i]*2),col("con_dsc_tx"),col("con_siz_tx"),lit(delim[i]*7)) )
        finalfile = df.toPandas()
        final = finalfile.values.tolist()
 
        filepathfinal = "/temp/pid/common/"+filepath[i]
        
          #using coalesce
        dataFrameWithOnlyOneColumn = df.select(concat(*df.columns).alias('data'))
        dataFrameWithOnlyOneColumn.coalesce(1).write.format("text").option("header", "false").mode("append").save(tempStorage)
        fp = dbutils.fs.ls(tempStorage)[-1].path[5:]
        print(fp)
        dbutils.fs.rm(filepathfinal)
        dbutils.fs.cp(fp, filepathfinal)
        
#   #looping to write
#         rct = 0 

#         with open("/dbfs"+filepathfinal, 'w', encoding='utf-8') as f:
#             for j in range(0, len(final)):
#                 rct+=1
#                 f.write(final[j][0])
#                 f.write("\n")

        #appending file header : row count and date of creation
        with open("/dbfs"+filepathfinal, "r+") as file:
#           for line in file:
#              print(line, end ="")
          content = file.readlines()
          rct = len(content)
#           file_data = file.read()
          file.seek(0, 0)
          file.write(str(rct)+delim[i]+date +delim[i]+ '\n')
          file.writelines(content)
#           file.write(str(rct)+delim[i]+date +delim[i]+ '\n'+ file_data)
#           file.write(file_data)


# COMMAND ----------

