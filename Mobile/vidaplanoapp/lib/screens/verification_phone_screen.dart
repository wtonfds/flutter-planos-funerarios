import 'package:carousel_slider/carousel_slider.dart';
import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:hexcolor/hexcolor.dart';
import 'package:sizer/sizer.dart';
import 'package:vidaplanoapp/screens/new_pass_screen.dart';

class VerificationPhoneScreen extends StatefulWidget {
  @override
  _VerificationPhoneScreenState createState() => _VerificationPhoneScreenState();
}

class _VerificationPhoneScreenState extends State<VerificationPhoneScreen> {

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
                  Text("Verificação", style: TextStyle(fontSize: 15.sp, color: HexColor("b63257")),),
                  SizedBox(height: 1.h,),
                  Text("Enviamos um SM para (48) 9****-0425 com um código \nde verificação, insira-o logo abaixo:", style: TextStyle(fontSize: 8.sp, fontWeight: FontWeight.w500), textAlign: TextAlign.center,),
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
                            hintText: "   __ __ __ __ __ __",
                            hintStyle: TextStyle(fontSize: 30.sp, color: Colors.grey.withOpacity(0.2)),
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
                      Navigator.push(context, MaterialPageRoute(builder: (context) => NewPassScreen()));
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
                  SizedBox(height: 1.h,),
                  Container(
                    width: 85.w,
                    child: Row(
                    children: [
                      GestureDetector(
                        onTap: (){
                          Navigator.pop(context);
                        },
                        child: Text("  Reenviar Código", style: TextStyle(fontSize: 9.sp, color: HexColor("1a6069")),),
                      ),
                      SizedBox(width: 40.w,),
                      GestureDetector(
                        onTap: (){
                          Navigator.pop(context);
                        },
                        child: Text("Alterar Número", style: TextStyle(fontSize: 9.sp, color: HexColor("1a6069"))),
                      )
                    ],
                  ),
                  ),
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