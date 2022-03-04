import 'package:flutter/material.dart';
import 'package:hexcolor/hexcolor.dart';
import 'package:sizer/sizer.dart';

class FragmentHome extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return Container(
      height: 100.h,
      child: Column(
        children: [
          Container(
            height: 50.h,
            child: Image.asset("assets/images/webview.png", fit: BoxFit.fill, width: 100.w,),
          ),
          Container(
            height: 25.h,
            color: HexColor("#ecf1fa"),
            child: Container(
              padding: EdgeInsets.all(1.h),
              child: Image.asset("assets/images/cartao.png", fit: BoxFit.fill, width: 90.w,)
            ),
          )
        ],
      )
    );
  }
}