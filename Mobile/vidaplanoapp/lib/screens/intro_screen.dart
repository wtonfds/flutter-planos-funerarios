import 'package:carousel_slider/carousel_slider.dart';
import 'package:flutter/material.dart';
import 'package:hexcolor/hexcolor.dart';
import 'package:sizer/sizer.dart';
import 'package:vidaplanoapp/screens/login_screen.dart';

class IntroScreen extends StatefulWidget {
  @override
  _IntroScreenState createState() => _IntroScreenState();
}

class _IntroScreenState extends State<IntroScreen> {
  int _current = 0;
  final CarouselController _controller = CarouselController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
      height: 100.h,
      color: HexColor("#ecf1fa"),
      child: Column(
        children: [
            Container(
              height: 30.h,
              child: Center(
                child: Image.asset("assets/images/logo_transparent.png", height: 15.h,),
              ),
            ),
            Container(
              height: 70.h,
              padding: EdgeInsets.only(left: 1.0.h, right: 1.0.h),
              child: Column(children: [
                Container(
                  height: 60.h,
                  child: Expanded(
                  child: CarouselSlider(
                    items: imageSliders,
                    carouselController: _controller,
                    options: CarouselOptions(
                        viewportFraction: 1,
                        height: 100.0.h,
                        enlargeCenterPage: true,
                        onPageChanged: (index, reason) {
                          setState(() {
                            if(index == 0 && _current == 2){
                              Navigator.pushReplacement(context, MaterialPageRoute(builder: (context) => LoginScreen()));
                            }
                            _current = index;
                          });
                        }),
                  ),
                ),
                ),
                Container(
                  padding: EdgeInsets.only(left: 2.h, right: 2.h),
                  height: 10.h,
                  child: Row(
                    children: [
                      Expanded(
                        flex: 3,
                        child: GestureDetector(
                          onTap: (){
                            _controller.previousPage();
                          },
                          child: Text("Anterior", style: TextStyle(fontSize: 9.sp, color: HexColor("19606b")),),
                        )
                      ),
                      Expanded(
                        flex: 4,
                        child: Row(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: imgList.asMap().entries.map((entry) {
                          return GestureDetector(
                            onTap: () => _controller.animateToPage(entry.key),
                            child: Container(
                              width: 12.0,
                              height: 8.0,
                              margin: EdgeInsets.symmetric(vertical: 8.0, horizontal: 4.0),
                              decoration: BoxDecoration(
                                  shape: BoxShape.circle,
                                  color: (Theme.of(context).brightness == Brightness.dark
                                          ? Colors.white
                                          : HexColor("19606b"))
                                      .withOpacity(_current == entry.key ? 0.9 : 0.4)),
                            ),
                          );
                        }).toList(),
                      ),
                      ),
                      Expanded(
                        flex: 3,
                        child: GestureDetector(
                          onTap: (){
                            _controller.nextPage();
                          },
                          child: Text("Próximo ", style: TextStyle(fontSize: 9.sp, color: HexColor("19606b")), textAlign: TextAlign.end),
                        )
                      )
                    ],
                  )
                )
                
                
              ]),
            )
            // Container(
            //   height: 60.h,
            //   child: Image.asset("assets/images/webview.png", fit: BoxFit.fitHeight,),
            // ),
            // Container(
            //   height: 10.h,
            //   child: Align(
            //     alignment: Alignment.bottomCenter,
            //     child: Row(
            //       children: [

            //       ],
            //     ),
            //   ),
            // )
          ]
        )
      )
    );
  }
}

final List<String> imgList = [
    'assets/images/webview.png',
    'assets/images/webview.png',
    'assets/images/webview.png',
  ];


final List<Widget> imageSliders = imgList
    .map((item) => Container(
          child: Container(
            width: 100.w,
            child: Image.asset(item, fit: BoxFit.cover, width: 100,)
          ),
        ))
    .toList();