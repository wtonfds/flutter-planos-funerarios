import 'package:flutter/material.dart';
import 'package:hexcolor/hexcolor.dart';
import 'package:sizer/sizer.dart';
import 'package:vidaplanoapp/screens/intro_screen.dart';

class SplashScreen extends StatefulWidget {
  @override
  _SplashScreenState createState() => _SplashScreenState();
}

class _SplashScreenState extends State<SplashScreen> {
  
  @override
  void initState() {
    super.initState();
    Future.delayed(Duration(seconds: 3), () {
      Navigator.pushReplacement(context, MaterialPageRoute(builder: (context) => IntroScreen()));
    });
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      color: HexColor("#ecf1fa"),
      child: Center(
        child: Padding(
          padding: EdgeInsets.only(left: 5.h, right: 5.h),
          child: Image.asset("assets/images/logo_transparent.png"),
        ),
      ),
    );
  }
}