package tn.farah.smartmakeupapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tn.farah.smartmakeupapp.Adapter.CategoryAdapter
import tn.farah.smartmakeupapp.Adapter.NewProductAdapter
import tn.farah.smartmakeupapp.data.models.Category
import tn.farah.smartmakeupapp.data.models.Product
import tn.farah.smartmakeupapp.data.repo.ProductApi

class AcceuilActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewNewProduct: RecyclerView
    private lateinit var newProducts: ArrayList<Product>

    private lateinit var newProductAdapter: NewProductAdapter

    private lateinit var categorys: ArrayList<Category>
    private lateinit var categoryAdapter: CategoryAdapter


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acceuil)

       getProduct()


        // Appel de la fonction getProduct() après la création de l'instance de ProductApi
//print(newProducts)
        recyclerView = findViewById(R.id.categoryList)

        recyclerView.layoutManager =
            GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false)

        categorys = ArrayList()
        categorys.add(Category("1", "Vegan"))
        categorys.add(Category("2", "Face"))
        categorys.add(Category("3", "Eye"))
        categorys.add(Category("4", "Lip"))
        categorys.add(Category("5", "cate4"))

        categoryAdapter = CategoryAdapter(categorys)
        recyclerView.adapter = categoryAdapter
        categoryAdapter.onItemClick = {
            val intent = Intent(this, product_by_category::class.java)
            intent.putExtra("category", it)
            startActivity(intent)
        }
//////////////////////////////new_product/////////////////////////////////////////////////
        recyclerViewNewProduct = findViewById(R.id.newInrecyclerView)
        recyclerViewNewProduct.layoutManager =
            GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false)
            newProducts= ArrayList()
        newProducts.add( Product("1","TOMFORD","Lip Color Matte Lipstick","",10.10,100,true,false,"","",true,20))
        newProducts.add( Product("1","p3","description","",60.20,100,true,false,"","",true,20))
        newProducts.add( Product("1","p4","description","",10.20,100,true,false,"","",true,20))
        newProducts.add( Product("1","p5","description","",100.50,100,true,false,"","",true,20))
        newProducts.add( Product("1","p6","description","",15.20,100,true,false,"","",true,20))
        newProducts.add( Product("1","p7","description","",66.00,100,true,false,"","",true,20))
        newProductAdapter = NewProductAdapter(newProducts)
        recyclerViewNewProduct.adapter = newProductAdapter

        newProductAdapter.onItemClick = {
            val intent = Intent(this, product_detail::class.java)
            intent.putExtra("product", it)
            startActivity(intent)

        }
    }

    fun showToast(str: String) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
    }
private fun getProduct(){
    try{
        val listResult = ProductApi.retrofitService.getProducts()
print(listResult)
    }catch (e:Exception){
        print("Failure: "+{e.message})
    }
//print(listResult)
}


}
