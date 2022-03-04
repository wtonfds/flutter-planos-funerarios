import 'package:carousel_slider/carousel_slider.dart';
import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:hexcolor/hexcolor.dart';
import 'package:sizer/sizer.dart';
import 'package:vidaplanoapp/screens/home_screen.dart';
import 'package:vidaplanoapp/screens/sign_up_complete_screen.dart';

class PaymentScreen extends StatefulWidget {
  @override
  _PaymentScreenState createState() => _PaymentScreenState();
}

class _PaymentScreenState extends State<PaymentScreen> {

   int? _radioValue = 0;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: HexColor("#ecf1fa"),
      body: SingleChildScrollView(
        child: Container(
        height: 100.h,
        child: Column(
          children: [
            Container(
              height: 5.h,
            ),
            Container(
              height: 90.h,
              child: Column(

                children: [
                  Image.asset("assets/images/logo_transparent.png", height: 15.h,),
                  SizedBox(height: 2.h,),
                  Text("Forma de Pagamento", style: TextStyle(fontSize: 15.sp, color: HexColor("b63257")),),
                  SizedBox(height: 2.h,),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      Radio(
                        value: 0,
                        groupValue: _radioValue,
                        onChanged: (int? value) {
                          setState(() {
                            _radioValue = value;
                          });
                        },
                      ),
                      Text(
                        'Boleto Bancário',
                        style: new TextStyle(fontSize: 10.sp),
                      ),
                       Radio(
                        value: 1,
                        groupValue: _radioValue,
                        onChanged: (int? value) {
                          setState(() {
                            _radioValue = value;
                          });
                        },
                      ),
                      Text(
                        'Cartão de Crédito',
                        style: new TextStyle(fontSize: 10.sp,),
                      ),
                      SizedBox(width: 8.w,)
                    ],
                  ),
                  SizedBox(height: 1.h,),
                  _radioValue == 0 ? _buildFormBoleto() : _buildFormCreditCard(),
                  SizedBox(height: 2.h,),
                  ElevatedButton(
                    onPressed: () {
                      Navigator.pushReplacement(context, MaterialPageRoute(builder: (context) => HomeScreen()));
                    },
                    child: Container(
                      width: 75.w,
                      height: 6.h,
                      child: Center(
                        child: Text("Finalizar Compra"),
                      ),
                    ),
                    style: ElevatedButton.styleFrom(
                      primary: HexColor("1a6069"),
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(12), // <-- Radius
                      ),
                    ),
                  ),
                  SizedBox(height: 1.h,),
                  GestureDetector(
                    onTap: (){
                      Navigator.pop(context);
                    },
                    child: Text("Voltar", style: TextStyle(fontSize: 10.sp, color: HexColor("1a6069")),),
                  )
                ],
              ),
            )
          ],
        ),
      ),
      )
    );
  }

  _buildFormBoleto(){
    return Container(
            width: 82.w,
            child: Column(
              children: [
                SizedBox(
                  height: 6.h,
                  child: TextFormField(
                  keyboardType: TextInputType.text,
                  textInputAction: TextInputAction.done,
                  decoration: InputDecoration(
                    contentPadding: const EdgeInsets.all(5.0),
                    errorStyle: TextStyle(color: Colors.white),
                    hintText: "Logradouro",
                    hintStyle: TextStyle(fontSize: 11.sp, color: Colors.grey.withOpacity(0.5)),
                    border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(10.0),
                        borderSide: BorderSide.none
                    ),
                    filled: true,
                    fillColor: Colors.white,
                  ),
                  //style: TextStyle(fontSize: 11.sp, color: Colors.grey.withOpacity(0.1)),
                  obscureText: false,
                ),
                ),
                SizedBox(height: 2.h,),
                SizedBox(
                  height: 6.h,
                  child: TextFormField(
                  keyboardType: TextInputType.text,
                  textInputAction: TextInputAction.done,
                  decoration: InputDecoration(
                    contentPadding: const EdgeInsets.all(5.0),
                    errorStyle: TextStyle(color: Colors.white),
                    hintText: "Número",
                    hintStyle: TextStyle(fontSize: 11.sp, color: Colors.grey.withOpacity(0.5)),
                    border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(10.0),
                        borderSide: BorderSide.none
                    ),
                    filled: true,
                    fillColor: Colors.white,
                  ),
                  //style: TextStyle(fontSize: 11.sp, color: Colors.grey.withOpacity(0.1)),
                  obscureText: false,
                ),
                ),
                SizedBox(height: 2.h,),
                SizedBox(
                  height: 6.h,
                  child: TextFormField(
                  keyboardType: TextInputType.text,
                  textInputAction: TextInputAction.done,
                  decoration: InputDecoration(
                    contentPadding: const EdgeInsets.all(5.0),
                    errorStyle: TextStyle(color: Colors.white),
                    hintText: "Bairro",
                    hintStyle: TextStyle(fontSize: 11.sp, color: Colors.grey.withOpacity(0.5)),
                    border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(10.0),
                        borderSide: BorderSide.none
                    ),
                    filled: true,
                    fillColor: Colors.white,
                  ),
                  // style: TextStyle(fontSize: 11.sp, color: Colors.grey.withOpacity(0.3)),
                  obscureText: false,
                ),
                ),
                SizedBox(height: 2.h,),
                SizedBox(
                  height: 6.h,
                  child: TextFormField(
                  keyboardType: TextInputType.text,
                  textInputAction: TextInputAction.done,
                  decoration: InputDecoration(
                    contentPadding: const EdgeInsets.all(5.0),
                    errorStyle: TextStyle(color: Colors.white),
                    hintText: "Cidade",
                    hintStyle: TextStyle(fontSize: 11.sp, color: Colors.grey.withOpacity(0.5)),
                    border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(10.0),
                        borderSide: BorderSide.none
                    ),
                    filled: true,
                    fillColor: Colors.white,
                  ),
                  // style: TextStyle(fontSize: 11.sp, color: Colors.grey.withOpacity(0.3)),
                  obscureText: false,
                ),
                ),
                SizedBox(height: 2.h,),
                SizedBox(
                  height: 6.h,
                  child: TextFormField(
                  keyboardType: TextInputType.text,
                  textInputAction: TextInputAction.done,
                  decoration: InputDecoration(
                    contentPadding: const EdgeInsets.all(5.0),
                    errorStyle: TextStyle(color: Colors.white),
                    hintText: "UF",
                    hintStyle: TextStyle(fontSize: 11.sp, color: Colors.grey.withOpacity(0.5)),
                    border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(10.0),
                        borderSide: BorderSide.none
                    ),
                    filled: true,
                    fillColor: Colors.white,
                  ),
                  // style: TextStyle(fontSize: 11.sp, color: Colors.grey.withOpacity(0.3)),
                  obscureText: false,
                ),
                ),
                SizedBox(height: 2.h,),
                  Container(
                    width: 80.w,
                    child: Text("Você receberá o boleto por email e SMS todos os meses para efetuar o pagamento. Fique atento ao vencimento."),
                  ),
              ],
            ),
          );
  }

  _buildFormCreditCard(){
    return Container(
            width: 82.w,
            child: Column(
              children: [
                SizedBox(height: 5.h,),
                SizedBox(
                  height: 6.h,
                  child: TextFormField(
                  keyboardType: TextInputType.text,
                  textInputAction: TextInputAction.done,
                  decoration: InputDecoration(
                    contentPadding: const EdgeInsets.all(5.0),
                    errorStyle: TextStyle(color: Colors.white),
                    hintText: "Número do Cartão",
                    hintStyle: TextStyle(fontSize: 11.sp, color: Colors.grey.withOpacity(0.5)),
                    border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(10.0),
                        borderSide: BorderSide.none
                    ),
                    filled: true,
                    fillColor: Colors.white,
                  ),
                  //style: TextStyle(fontSize: 11.sp, color: Colors.grey.withOpacity(0.1)),
                  obscureText: false,
                ),
                ),
                SizedBox(height: 2.h,),
                SizedBox(
                  height: 6.h,
                  child: TextFormField(
                  keyboardType: TextInputType.text,
                  textInputAction: TextInputAction.done,
                  decoration: InputDecoration(
                    contentPadding: const EdgeInsets.all(5.0),
                    errorStyle: TextStyle(color: Colors.white),
                    hintText: "Nome do TItular do Cartão",
                    hintStyle: TextStyle(fontSize: 11.sp, color: Colors.grey.withOpacity(0.5)),
                    border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(10.0),
                        borderSide: BorderSide.none
                    ),
                    filled: true,
                    fillColor: Colors.white,
                  ),
                  // style: TextStyle(fontSize: 11.sp, color: Colors.grey.withOpacity(0.3)),
                  obscureText: false,
                ),
                ),
                SizedBox(height: 2.h,),
                SizedBox(
                  height: 6.h,
                  child: TextFormField(
                  keyboardType: TextInputType.text,
                  textInputAction: TextInputAction.done,
                  decoration: InputDecoration(
                    contentPadding: const EdgeInsets.all(5.0),
                    errorStyle: TextStyle(color: Colors.white),
                    hintText: "CPF do Titular do Cartão",
                    hintStyle: TextStyle(fontSize: 11.sp, color: Colors.grey.withOpacity(0.5)),
                    border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(10.0),
                        borderSide: BorderSide.none
                    ),
                    filled: true,
                    fillColor: Colors.white,
                  ),
                  // style: TextStyle(fontSize: 11.sp, color: Colors.grey.withOpacity(0.3)),
                  obscureText: false,
                ),
                ),
                SizedBox(height: 2.h,),
                SizedBox(
                  height: 6.h,
                  child: TextFormField(
                  keyboardType: TextInputType.text,
                  textInputAction: TextInputAction.done,
                  decoration: InputDecoration(
                    contentPadding: const EdgeInsets.all(5.0),
                    errorStyle: TextStyle(color: Colors.white),
                    hintText: "Vencimento do Cartão",
                    hintStyle: TextStyle(fontSize: 11.sp, color: Colors.grey.withOpacity(0.5)),
                    border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(10.0),
                        borderSide: BorderSide.none
                    ),
                    filled: true,
                    fillColor: Colors.white,
                  ),
                  // style: TextStyle(fontSize: 11.sp, color: Colors.grey.withOpacity(0.3)),
                  obscureText: false,
                ),
                ),
                SizedBox(height: 2.h,),
                SizedBox(
                  height: 6.h,
                  child: TextFormField(
                  keyboardType: TextInputType.text,
                  textInputAction: TextInputAction.done,
                  decoration: InputDecoration(
                    contentPadding: const EdgeInsets.all(5.0),
                    errorStyle: TextStyle(color: Colors.white),
                    hintText: "CVC",
                    hintStyle: TextStyle(fontSize: 11.sp, color: Colors.grey.withOpacity(0.5)),
                    border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(10.0),
                        borderSide: BorderSide.none
                    ),
                    filled: true,
                    fillColor: Colors.white,
                  ),
                  // style: TextStyle(fontSize: 11.sp, color: Colors.grey.withOpacity(0.3)),
                  obscureText: false,
                ),
                ),
                SizedBox(height: 3.h,),
              ],
            ),
          );
  }
}