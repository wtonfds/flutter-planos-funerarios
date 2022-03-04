import 'package:flutter/material.dart';
import 'package:hexcolor/hexcolor.dart';
import 'package:sizer/sizer.dart';

class FragmentFunerary extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return Container(
      height: 100.h,
      child: Column(
        children: [
          Container(
            padding: EdgeInsets.only(left: 2.h, top: 2.h, right: 10.h),
            decoration: BoxDecoration(
              color: HexColor("1a6069"),
              borderRadius: const BorderRadius.only(bottomLeft: Radius.circular(20), bottomRight: Radius.circular(20)),
            ),
            height: 20.h,
            width: 100.w,
            child: Column(
              children: [
                Row(
                  children: [
                    Text("Vida Plano", style: TextStyle(color: Colors.white, fontSize: 22.sp, fontWeight: FontWeight.w500),)
                  ],
                ),
                SizedBox(
                  height: 2.h,
                ),
                Row(
                  children: [
                    Expanded(child: Text("Atuando em todo o território nacional, o Vida Plano oferece Assistência Familiar, além de vários benefícios disponíveis em uma plataforma 100% digital.", 
                    style: TextStyle(color: Colors.white, fontSize: 10.sp),),)
                    
                  ],
                )
                
              ],
            ),
          ),
          Container(
            padding: EdgeInsets.only(left: 2.h, right: 2.h),
            height: 55.h,
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                 SizedBox(
                  height: 8.h,
                ),
                Card(
                  child: Container(
                    padding: EdgeInsets.only(left: 5.h, right: 3.h),
                    height: 13.h,
                    child: Row(
                      // ignore: prefer_const_literals_to_create_immutables
                      children: [
                        // ignore: prefer_const_constructors
                       Expanded(
                          flex: 8,
                          child: Column(
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: [
                              Row(
                                children: [
                                  Text("(16) 3512-6879", style: TextStyle(color: HexColor("1a6069"), fontSize: 13.sp, fontWeight: FontWeight.w500),),
                                ],
                              ),
                              SizedBox(
                                height: 1.0.h,
                              ),
                              Row(
                                children: [
                                  Expanded(
                                    child: Text("Clique para realizar uma ligação", style: TextStyle(color: HexColor("1a6069"), fontSize: 8.sp)),
                                    flex: 6,
                                  ),
                                  Expanded(
                                    flex: 4,
                                    child: Row(),
                                  )
                                ],
                              )
                            ],
                          ),
                        ),
                        // ignore: prefer_const_constructors
                        Expanded(
                          flex: 2,
                          child: Image.asset("assets/images/phone_icon.png", height: 6.h),
                        )
                      ],
                    )
                  )
                ),
                SizedBox(
                  height: 4.h,
                ),
                Card(
                  child: Container(
                    padding: EdgeInsets.only(left: 5.h, right: 3.h),
                    height: 13.h,
                    child: Row(
                      // ignore: prefer_const_literals_to_create_immutables
                      children: [
                        // ignore: prefer_const_constructors
                        Expanded(
                          flex: 8,
                          child: Column(
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: [
                              Row(
                                children: [
                                  Text("(16) 4000-1600", style: TextStyle(color: HexColor("1a6069"), fontSize: 13.sp, fontWeight: FontWeight.w500),),
                                ],
                              ),
                              SizedBox(
                                height: 1.0.h,
                              ),
                              Row(
                                children: [
                                  Expanded(
                                    child: Text("Clique para iniciar o atendimento pelo Whatsapp", style: TextStyle(color: HexColor("1a6069"), fontSize: 8.sp)),
                                    flex: 6,
                                  ),
                                  Expanded(
                                    flex: 4,
                                    child: Row(),
                                  )
                                ],
                              )
                            ],
                          ),
                        ),
                        // ignore: prefer_const_constructors
                        Expanded(
                          flex: 2,
                          child: Image.asset("assets/images/wpp_icon.png", height: 10.h,),
                        )
                      ],
                    )
                  )
                ),
                SizedBox(
                  height: 6.h,
                ),
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Image.asset("assets/images/logo_transparent.png", height: 8.h,)
                  ],
                )
              ],
            ),
          )
        ]
      )
    );
  }
}