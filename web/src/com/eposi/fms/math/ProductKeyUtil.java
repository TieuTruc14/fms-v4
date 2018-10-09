package com.eposi.fms.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by TienManh on 4/6/2016.
 */
public class ProductKeyUtil {

    /**
     * Tạo product_key với tiền tố modelId+batchId+ series gồm 5 ký tự không có chữ i/I và o/O.
     * @param modelId
     * @param batchId
     * @return
     */
    public static String genKey(String modelId, String batchId){

        List<Character> symbols= new ArrayList<>();
        for (char ch = '0'; ch <= '9'; ++ch)
            symbols.add(ch);
        for (char ch = 'A'; ch <= 'H'; ++ch)
            symbols.add(ch);
        for (char ch = 'J'; ch <= 'N'; ++ch)
            symbols.add(ch);
        for (char ch = 'P'; ch <= 'Z'; ++ch)
            symbols.add(ch);
        for (char ch = 'a'; ch <= 'h'; ++ch)
            symbols.add(ch);
        for (char ch = 'j'; ch <= 'n'; ++ch)
            symbols.add(ch);
        for (char ch = 'p'; ch <= 'z'; ++ch)
            symbols.add(ch);

        Random random = new Random();
        String key=modelId+batchId;
        for (int idx = 0; idx < 5; ++idx)
            key += symbols.get(random.nextInt(symbols.size()));


        return key;
    }

    public static void main(String[] args) {

        for(int i=0;i<10;i++){
            String key= genKey("GH12", "001");
            System.out.println(key);
        }


    }


}
