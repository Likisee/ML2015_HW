c:
cd \
cd C:\libsvm-3.20\windows\
svm-train.exe -t 2 -d 2 -g 10000 -c 0.1 q20_train_1000.txt
svm-predict.exe q20_rest.txt q20_train_1000.txt.model output.txt > q20_eval_result.txt
exit(1)
