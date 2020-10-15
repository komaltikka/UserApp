package com.example.userott.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.userott.R;
import com.example.userott.activities.Product;
import com.example.userott.activities.RecyclerviewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements  BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    private SliderLayout sliderLayout;
    private HashMap<String, Integer> sliderImages;
    private  View view;
    private RecyclerView recyclerView;
    private List<Product> products;
    private RecyclerviewAdapter recyclerviewAdapter;



    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,container,false);
        this.view=view;
        recyclerView =view.findViewById(R.id.recyclerview);
        sliderLayout = view.findViewById(R.id.sliderLayout);
        sliderImages = new HashMap<>();
        setupSlider();


        return view;
    }

    private void setupSlider() {
        sliderLayout=view.findViewById(R.id.sliderLayout);
        sliderImages = new HashMap<>();
        sliderImages.put("Great Deal", R.drawable.images1);
        sliderImages.put("New Deal Every Hour",R.drawable.imag2);
        sliderImages.put(" Sale", R.drawable.images1);
        sliderImages.put("",R.drawable.download1);
        sliderImages.put("Great Deals", R.drawable.download2);
        sliderImages.put("", R.drawable.images12);

        for (String name : sliderImages.keySet()) {

            TextSliderView textSliderView = new TextSliderView(getContext());
            textSliderView
                    .description(name)
                    .image(sliderImages.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(3000);



        recyclerviewAdapter = new RecyclerviewAdapter(getContext(),products);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerviewAdapter);
        loadProducts();

    }




    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    private void loadProducts(){

        products = new ArrayList<>();
        Product product1 = new Product();
        product1.setpName("Amazon Prime ");
        product1.setpOffer(" + Upto 7.5% Extra cashback");
        product1.setpImage(R.drawable.download1);
        products.add(product1);
        Product product2 = new Product();
        product2.setpName("Netflix");
        product2.setpOffer(" + Upto 8.5% Extra cashback");
        product2.setpImage(R.drawable.download2);
        products.add(product2);
        Product product3 = new Product();
        product3.setpName("Hotstar");
        product3.setpOffer(" + Upto 2.5% Extra cashback");
        product3.setpImage(R.drawable.images12);
        products.add(product3);
        Product product4 = new Product();
        product4.setpName("Netfilx");
        product4.setpOffer(" + Upto 7.5% Extra cashback");
        product4.setpImage(R.drawable.download1);
        products.add(product4);
        Product product5 = new Product();
        product5.setpName("DittoTV");
        product5.setpOffer(" + Upto 7.5% Extra cashback");
        product5.setpImage(R.drawable.download2);
        products.add(product5);
        Product product6 = new Product();
        product6.setpName("Voot");
        product6.setpOffer(" + Upto 7.5% Extra cashback");
        product6.setpImage(R.drawable.download1);
        products.add(product6);

        recyclerviewAdapter.setProducts(products);
    }


    @Override
    public void onStop() {
        sliderLayout.stopAutoCycle();
        super.onStop();
    }

}