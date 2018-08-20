#------------------
#   echo_color
#   对于一些特殊操作进行颜色提示
#------------------
echoc()
{
    echo -e "\033[36m $1 \033[0m"
}

#-------------------------------
#   echo_error
#   执行过程中产生错误的的信息提示
#-------------------------------
echoe()
{
    echo -e "\033[31m $1 \033[0m"
}

echoc "rn.sh react_native_bundle"
echoc "生成common.bundle"
react-native bundle --platform android --dev false --entry-file common/index.js  --bundle-output android/app/src/main/assets/common.bundle  --assets-dest android/app/src/main/res/
echoc "生成subA.bundle"
react-native bundle --platform android --dev false --entry-file subA/subA.js  --bundle-output android/app/src/main/assets/subA.bundle  --assets-dest android/app/src/main/res/
echoc "生成subB.bundle"
react-native bundle --platform android --dev false --entry-file subB/subB.js  --bundle-output android/app/src/main/assets/subB.bundle  --assets-dest android/app/src/main/res/
echoc "生成subC.bundle"
react-native bundle --platform android --dev false --entry-file subC/subC.js  --bundle-output android/app/src/main/assets/subC.bundle  --assets-dest android/app/src/main/res/
echoc "生成差分subA_.bundle文件"
comm -2 -3 android/app/src/main/assets/subA.bundle android/app/src/main/assets/common.bundle > android/app/src/main/assets/subA_.bundle
echoc "生成差分subB_.bundle文件"
comm -2 -3 android/app/src/main/assets/subB.bundle android/app/src/main/assets/common.bundle > android/app/src/main/assets/subB_.bundle
echoc "生成差分subC_.bundle文件"
comm -2 -3 android/app/src/main/assets/subC.bundle android/app/src/main/assets/common.bundle > android/app/src/main/assets/subC_.bundle