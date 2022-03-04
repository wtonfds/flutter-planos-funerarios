import 'package:carousel_slider/carousel_slider.dart';
import 'package:flutter/material.dart';
import 'package:hexcolor/hexcolor.dart';
import 'package:sizer/sizer.dart';
import 'package:vidaplanoapp/data/api_facebook.dart';
import 'package:vidaplanoapp/data/api_google.dart';
import 'package:vidaplanoapp/screens/login_cpf_screen.dart';
import 'package:vidaplanoapp/screens/plans_screen.dart';
import 'package:vidaplanoapp/screens/recovery_pass_screen.dart';
import 'package:vidaplanoapp/screens/sign_up_screen.dart';

class LoginScreen extends StatefulWidget {
  @override
  _LoginScreenState createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  
  final ApiGoogle apiGoogle = ApiGoogle();
  final ApiFacebook apiFacebook = ApiFacebook();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: HexColor("#ecf1fa"),
      body: Container(
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
                  Text("Bem Vindo", style: TextStyle(fontSize: 15.sp, color: HexColor("b63257")),),
                  SizedBox(height: 1.h,),
                  Text("Entre para continuar", style: TextStyle(fontSize: 8.sp, fontWeight: FontWeight.w500),),
                  SizedBox(height: 4.h,),
                  ElevatedButton(
                    onPressed: () {
                      Navigator.push(context, MaterialPageRoute(builder: (context) => LoginCPFScreen()));
                    },
                    child: Container(
                      width: 75.w,
                      height: 6.h,
                      child: Center(
                        child: Text("Entrar com CPF"),
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
                  Text("Ou"),
                  SizedBox(height: 2.h,),
                  OutlinedButton(
                    onPressed: () async {
                        apiGoogle.signInWithGoogle().then((value){
                          if(value.user != null){
                            Navigator.push(context, MaterialPageRoute(builder: (context) => PlansScreen()));
                          }
                        });
                    },
                    child: Container(
                      width: 75.w,
                      height: 6.h,
                      child: Center(
                        child: Row(
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: [
                            Image.asset("assets/images/google.png", height: 3.h,),
                            SizedBox(width: 3.w,),
                            Text("Entrar com o Google")
                          ],
                        ),
                      ),
                    ),
                    style: OutlinedButton.styleFrom(
                      primary: HexColor("1a6069"),
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(12), // <-- Radius
                      ),
                    ),
                  ),
                  SizedBox(height: 1.h,),
                  ElevatedButton(
                    onPressed: () {
                      apiFacebook.signInWithFacebook().then((value){
                          if(value.user != null){
                            Navigator.push(context, MaterialPageRoute(builder: (context) => PlansScreen()));
                          }
                        });
                    },
                    child: Container(
                      width: 75.w,
                      height: 6.h,
                      child: Center(
                        child: Row(
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: [
                            Image.asset("assets/images/facebook.png", height: 4.h,),
                            SizedBox(width: 1.w,),
                            Text("Entrar com o Facebook")
                          ],
                        ),
                      )
                    ),
                    style: ElevatedButton.styleFrom(
                      primary: HexColor("3a559f"),
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
                          Navigator.push(context, MaterialPageRoute(builder: (context) => SignUpScreen()));
                        },
                        child: Text("Não possuo cadastro", style: TextStyle(fontSize: 9.sp, color: HexColor("1a6069")),),
                      ),
                      SizedBox(width: 27.w,),
                      GestureDetector(
                        onTap: (){
                          Navigator.push(context, MaterialPageRoute(builder: (context) => RecoveryPassScreen()));
                        },
                        child: Text("Esqueci minha senha", style: TextStyle(fontSize: 9.sp, color: HexColor("1a6069"))),
                      )
                    ],
                  ),
                  ),
                  SizedBox(height: 15.h,),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      Text("Ao entrar você aceita os ", style: TextStyle(fontSize: 9.sp, color: Colors.black)),
                      Text("Termos e Condições", style: TextStyle(fontSize: 9.sp, color: HexColor("1a6069"))),
                    ],
                  )

                ],
              ),
            )
          ],
        ),
      )
    );
  }
}