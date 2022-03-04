import 'package:carousel_slider/carousel_slider.dart';
import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:hexcolor/hexcolor.dart';
import 'package:sizer/sizer.dart';
import 'package:vidaplanoapp/screens/login_cpf_screen.dart';
import 'package:vidaplanoapp/screens/verification_phone_screen.dart';

class NewPassScreen extends StatefulWidget {
  @override
  _NewPassScreenState createState() => _NewPassScreenState();
}

class _NewPassScreenState extends State<NewPassScreen> {

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
              height: 20.h,
            ),
            Container(
              height: 80.h,
              child: Column(
                children: [
                  Image.asset("assets/images/logo_transparent.png", height: 15.h,),
                  SizedBox(height: 4.h,),
                  Text("Nova Senha", style: TextStyle(fontSize: 15.sp, color: HexColor("b63257")),),
                  SizedBox(height: 1.h,),
                  Text("Insira sua senha", style: TextStyle(fontSize: 8.sp, fontWeight: FontWeight.w500),),
                  SizedBox(height: 7.h,),
                  Container(
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
                            prefixIcon: Container(
                                padding: const EdgeInsets.only(top: 5.0, bottom: 5.0),
                                margin: const EdgeInsets.only(right: 5.0),
                                decoration: BoxDecoration(
                                    color: Colors.white,
                                    borderRadius: BorderRadius.only(
                                        topLeft: Radius.circular(10.0),
                                        bottomLeft: Radius.circular(10.0),
                                        topRight: Radius.circular(0.0),
                                        bottomRight: Radius.circular(0.0)
                                    )
                                ),
                                child: Icon(FontAwesomeIcons.lock, color: Colors.grey, size: 3.h,)),
                                suffixIcon: Container(
                                  padding: const EdgeInsets.only(top: 5.0, bottom: 5.0),
                                  margin: const EdgeInsets.only(right: 5.0),
                                  decoration: BoxDecoration(
                                      color: Colors.white,
                                      borderRadius: BorderRadius.only(
                                          topLeft: Radius.circular(10.0),
                                          bottomLeft: Radius.circular(10.0),
                                          topRight: Radius.circular(0.0),
                                          bottomRight: Radius.circular(0.0)
                                      )
                                  ),
                                  child: Icon(FontAwesomeIcons.eye, color: Colors.grey.withOpacity(0.3), size: 2.h,)),
                            hintText: "Senha",
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
                            prefixIcon: Container(
                                padding: const EdgeInsets.only(top: 5.0, bottom: 5.0),
                                margin: const EdgeInsets.only(right: 5.0),
                                decoration: BoxDecoration(
                                    color: Colors.white,
                                    borderRadius: BorderRadius.only(
                                        topLeft: Radius.circular(10.0),
                                        bottomLeft: Radius.circular(10.0),
                                        topRight: Radius.circular(0.0),
                                        bottomRight: Radius.circular(0.0)
                                    )
                                ),
                                child: Icon(FontAwesomeIcons.lock, color: Colors.grey, size: 3.h,)),
                                suffixIcon: Container(
                                  padding: const EdgeInsets.only(top: 5.0, bottom: 5.0),
                                  margin: const EdgeInsets.only(right: 5.0),
                                  decoration: BoxDecoration(
                                      color: Colors.white,
                                      borderRadius: BorderRadius.only(
                                          topLeft: Radius.circular(10.0),
                                          bottomLeft: Radius.circular(10.0),
                                          topRight: Radius.circular(0.0),
                                          bottomRight: Radius.circular(0.0)
                                      )
                                  ),
                                  child: Icon(FontAwesomeIcons.eye, color: Colors.grey.withOpacity(0.3), size: 2.h,)),
                            hintText: "Confirmar Senha",
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
                      ],
                    ),
                  ),
                  SizedBox(height: 10.h,),
                  ElevatedButton(
                    onPressed: () {
                      Navigator.pushReplacement(context, MaterialPageRoute(builder: (context) => LoginCPFScreen()));
                    },
                    child: Container(
                      width: 75.w,
                      height: 6.h,
                      child: Center(
                        child: Text("Continuar"),
                      ),
                    ),
                    style: ElevatedButton.styleFrom(
                      primary: HexColor("1a6069"),
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(12), // <-- Radius
                      ),
                    ),
                  ),
                  SizedBox(height: 2.h,),
                  // GestureDetector(
                  //   onTap: (){
                  //     Navigator.pop(context);
                  //   },
                  //   child: Text("Voltar", style: TextStyle(fontSize: 10.sp, color: HexColor("1a6069")),),
                  // )
                ],
              ),
            )
          ],
        ),
      ),
      )
    );
  }
}