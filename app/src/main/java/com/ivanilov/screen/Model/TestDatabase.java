package com.ivanilov.screen.Model;

import android.content.Context;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import ru.evotor.framework.inventory.InventoryApi;
import ru.evotor.framework.inventory.ProductItem;
import ru.evotor.framework.inventory.ProductQuery;

public class TestDatabase {

    public List<ProductItem> getAllProduct (Context context) {

        try {
            ProductQuery productQuery = new ProductQuery();

            List<ProductItem> productItemEvotor = productQuery.price.lower(BigDecimal.valueOf(100000), false)
                    .execute(context)
                    .toList();


            ArrayList <ProductItem> productItemEvotor1 = new ArrayList<>();

            for(int i =0; i<productItemEvotor.size(); i++){
                if (productItemEvotor.get(i).getClass() != ProductItem.ProductGroup.class && productItemEvotor.get(i).getParentUuid() != null){
                    productItemEvotor1.add(productItemEvotor.get(i));
                }
            }

            return productItemEvotor1;
        }
        catch (Exception e){

            List<ProductItem> productItems = new ArrayList<>();

            for (Integer j=0; j<10;j++) {
                for (Integer i = 0; i < 10; i++) {

                    if (i==3){
                        ProductItem productItem = new ProductItem.ProductGroup(
                                "5c21cef3-08ce-4eb4-8d89-d860ddaf3660".concat(i.toString()),
                                "496319c5-40a2-44e2-ab75-805575ee6a5".concat(j.toString()),
                                "103",
                                "Group ".concat(j.toString()),
                                ru.evotor.framework.receipt.TaxNumber.valueOf("VAT_18")

                        );
                        productItems.add(productItem);
                    }

                    else {
                        ProductItem.Product productItem = new ProductItem.Product(
                                "5c21cef3-08ce-4eb4-8d89-d860ddaf3660".concat(i.toString()),
                                "496319c5-40a2-44e2-ab75-805575ee6a5".concat(j.toString()),
                                "104",
                                "Product longname".concat(j.toString()).concat(i.toString()),
                                ru.evotor.framework.receipt.TaxNumber.valueOf("VAT_18"),
                                ru.evotor.framework.inventory.ProductType.valueOf("NORMAL"),
                                BigDecimal.valueOf(18.22).setScale(2),
                                BigDecimal.valueOf(0),
                                "Это описание продуктка",
                                "шт",
                                Integer.valueOf(0),
                                BigDecimal.valueOf(0)
                                ,
                                Long.valueOf(0),
                                BigDecimal.valueOf(0)

                        );
                        productItems.add(productItem);
                    }



                }
            }

            return productItems;
        }


    }

    public List<String> getGroupName(Context context){

        List<ProductItem> products = getAllProduct(context);
        List<String> groupName = new ArrayList<>();

        for (ProductItem i : products){

            String s = (getProductByUuid(context, i.getParentUuid())).getName();

            if (groupName.contains(s)){}
            else groupName.add(s);

        }
        return groupName;

    }

    public ProductItem getProductByUuid (Context context, String uuid){

        ProductItem productItem =  null;

        ProductItem productItemEvotor = InventoryApi.getProductByUuid(context, uuid);
        if (productItemEvotor != null) return productItemEvotor;

        else {

            for (ProductItem i : createProductItem()) {
                if (i.getUuid().equals(uuid)) {
                    productItem = i;
                }
            }
            return productItem;

        }
    }

    public ArrayList<ProductItem> getProductByName (Context context, ArrayList<String> name) {
        List<ProductItem> productItems = getAllProduct(context);
        ArrayList<ProductItem> result = new ArrayList<>();

        for (String j : name) {
            for (ProductItem i: productItems) {
                if (i.getName().equals(j)) {
                    result.add(i);
                }
            }
        }
        return result;

    }

    public ArrayList<ProductItem> getProductByUuid (Context context, ArrayList<String> uuid) {
        List<ProductItem> productItems = getAllProduct(context);
        ArrayList<ProductItem> result = new ArrayList<>();

        for (String j : uuid) {
            for (ProductItem i: productItems) {
                if (i.getUuid().equals(j)) {
                    result.add(i);
                }
            }
        }
        return result;

    }

    public List<ProductItem>  createProductItem (){

        List<ProductItem> productItems = new ArrayList<ProductItem>();

        for (Integer j=0; j<10;j++) {
            ProductItem productItemGroup = new ProductItem.ProductGroup(
                    "496319c5-40a2-44e2-ab75-805575ee6a5".concat(j.toString()),
                    null,
                    "103",
                    "Group ".concat(j.toString()),
                    ru.evotor.framework.receipt.TaxNumber.valueOf("VAT_18")
            );

            productItems.add(productItemGroup);

            for (Integer i = 0; i < 10; i++) {

                ProductItem productItem = new ProductItem.Product(
                        "5c21cef3-08ce-4eb4-8d89-d860ddaf3660".concat(i.toString()),
                        "496319c5-40a2-44e2-ab75-805575ee6a5".concat(j.toString()),
                        "104",
                        "Product longname".concat(j.toString()).concat(i.toString()),
                        ru.evotor.framework.receipt.TaxNumber.valueOf("VAT_18"),
                        ru.evotor.framework.inventory.ProductType.valueOf("NORMAL"),
                        BigDecimal.valueOf(18.22).setScale(2),
                        BigDecimal.valueOf(0),
                        "Это описание продуктка",
                        "шт",
                        Integer.valueOf(0),
                        BigDecimal.valueOf(0)
                        ,
                        Long.valueOf(0),
                        BigDecimal.valueOf(0)

                );

                productItems.add(productItem);
            }
        }
        return productItems;

    }


    public List<String> getProductByGroupName (String s, Context context){

        List<ProductItem> products = getAllProduct(context);

        List<String> result = new ArrayList<>();

        for (ProductItem i: products){

            String groupName = (getProductByUuid(context, i.getParentUuid())).getName();

            if (groupName.equals(s) && i.getClass() != ProductItem.ProductGroup.class ){
                result.add(i.getName());
            }

        }
        return result;

    }

    public List<String> getPriseProductByGroupName (String s, Context context){

        List<ProductItem> products = getAllProduct(context);

        List<String> result = new ArrayList<>();

        for (ProductItem i: products){

            String groupName = (getProductByUuid(context,  i.getParentUuid()).getName());

            if (groupName.equals(s) && i.getClass() != ProductItem.ProductGroup.class ){
                ProductItem.Product a = (ProductItem.Product) i;
                result.add(a.getPrice().toString());
            }

        }
        return result;

    }


    public List<ProductItem> getProducItemtByGroupName (String s, Context context){

        List<ProductItem> products = getAllProduct(context);

        List<ProductItem> result = new ArrayList<>();

        for (ProductItem i : products){

            String groupName = (getProductByUuid(context, i.getParentUuid())).getName();

            if (groupName.equals(s) && i.getClass() != ProductItem.ProductGroup.class ){
                result.add(i);
            }

        }
        return result;

    }


}
