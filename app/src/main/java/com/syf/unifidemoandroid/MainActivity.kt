package com.syf.unifidemoandroid

import TipFormModal
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

class MainActivity : AppCompatActivity() {
    private lateinit var dbuyButton: Button
    private lateinit var syfButtonClick : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapper = jacksonObjectMapper()
        // get reference to button
        dbuyButton = findViewById(R.id.button_dbuy)
        //HdA/CECiAxRB8UjOp4cPDA==
        //5348120940099971
        //5348123120078880 GCCYCmiOW6TnHuv+KiinKmt6jcDBVjug
        val dbuyTipFormModal = DBuyTipFormModal( "3", "53481231200788801680252673856", "5348123120078880", "","", "Optima","Kannan","38460","","","","","900 oak grove rd","","8663968254","a@a.com","goodspring","tn","","240","900","","","","","5348123120078880","","")
        val dbuyPayload = mapper.writeValueAsString(dbuyTipFormModal)

        // set on-click listener
        dbuyButton.setOnClickListener {
            val intent = Intent(this, DBuyWebviewActivity::class.java)
            intent.putExtra("jsonData",dbuyPayload)
            startActivity(intent)
        }

        // get reference to button
        syfButtonClick = findViewById(R.id.button_syf)
        var unifyJsonData = "{syfPartnerId:\"PI53421676\"," +
                "tokenId:\"PI53421676628481871380e57a\"," +
                "encryptKey:\"\",modalType:\"\",childMid:\"\",childPcgc:\"\"," +
                "childTransType:\"\",pcgc:\"\",partnerCode:\"\",clientToken:\"\"," +
                "postbackid:\"d979e5b7-6382-4e4e-b269-aab027bbed58\",clientTransId:\"\"," +
                "cardNumber:\"\",custFirstName:\"\",custLastName:\"\",expMonth:\"\",expYear:\"\"," +
                "custZipCode:\"\",custAddress1:\"\",phoneNumb:\"\",appartment:\"\",emailAddr:\"\"," +
                "custCity:\"\",upeProgramName:\"\",custState:\"\",transPromo1:\"\"" +
                ",iniPurAmt:\"\",mid:\"\",productCategoryNames:\"\",transAmount1:\"700\"," +
                "transAmounts:\"\",initialAmount:\"\",envUrl:\"https://dpdpone.syfpos.com/mitservice/\"," +
                "productAttributes:\"\",processInd:\"3\"}"

        var tipFormModal = TipFormModal("PI53421676", "18733b99b39PI5342167615895", "", "", "", "", "", "", "", "", "8c9f9578-7532-461f-ab11-9054d017be12", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "1000", "", "1000", "", "", "","", "", "","", "", "https://dpdpone.syfpos.com/mitservice/","3")
        val unifyJsonData1 = mapper.writeValueAsString(tipFormModal)
        // set on-click listener
        syfButtonClick.setOnClickListener {
            val intent = Intent(this, UniFiWebviewActivity::class.java)
            intent.putExtra("jsonData", unifyJsonData1)
            startActivity(intent)
        }
    }
}


