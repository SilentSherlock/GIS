package com.programs.gis.function;


/**
 * Author:  SilentSherlock
 * Date: 2020/4/11
 * Time: 15:24
 */

/*这是项目中使用的工具类，包含了公用的小方法*/
public class Tools {

    /*将数据中以"——"或"-"形式分割的数据转换为一小数点分割的形式，并返回浮点数*/
    public float transSeparator2dot(String DRT){
        String[] characters;
        if (DRT.contains("-")){
            characters = DRT.split("-");
        }else{
            characters = DRT.split("——");
        }

        StringBuilder resultStr = new StringBuilder();
        for (int i = 1;i < characters.length;i++){
            resultStr.append(characters[i]);
        }
        resultStr.insert(0, characters[0] + ".");
        return Float.parseFloat(resultStr.toString());
    }

    /*上述方法的逆过程*/
    public String transDot2Separator(Float DRT){
        StringBuilder stringBuilder = new StringBuilder();
        String DRTStr = DRT.toString();
        for (int i = 0;i < DRTStr.length();i++){
            if (DRTStr.charAt(i) >= '0' && DRTStr.charAt(i) <= '9'){
                stringBuilder.append(DRTStr.charAt(i)).append("-");
            }
        }
        return stringBuilder.substring(0, stringBuilder.length()-2);
    }
}
