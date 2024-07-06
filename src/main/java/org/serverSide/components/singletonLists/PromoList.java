package org.serverSide.components.singletonLists;

import org.serverSide.components.factories.SingletonListsFactory;
import org.serverSide.components.units.Promo;
import org.serverSide.components.units.Unit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PromoList extends SingletonListAbstract{
    private static PromoList instance;

    private PromoList(){
        super();
        try{
            list = SingletonListsFactory.createSingletonList("src/promoDatabase.json","promoList");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static PromoList getInstance(){
        if(instance == null){
            instance = new PromoList();
        }
        return instance;
    }

    public synchronized List<Unit> getPromoList(){ return new ArrayList<>(list);
    }

    public void remove(String promoCode){
        for(Unit uP : getPromoList()){
            Promo p = (Promo) uP;
            if(p.getCode().equals(promoCode)){
                remove(uP);
            }
        }
    }
}
