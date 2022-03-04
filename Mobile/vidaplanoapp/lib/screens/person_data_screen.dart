import 'package:carousel_slider/carousel_slider.dart';
import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:hexcolor/hexcolor.dart';
import 'package:sizer/sizer.dart';
import 'package:vidaplanoapp/screens/sign_up_complete_screen.dart';

class PersonDataScreen extends StatefulWidget {
  @override
  _PersonDataScreenState createState() => _PersonDataScreenState();
}

class _PersonDataScreenState extends State<PersonDataScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        backgroundColor: HexColor("#ecf1fa"),
        appBar: AppBar(
          backgroundColor: Colors.white,
          automaticallyImplyLeading: false,
          centerTitle: false,
          title: Text("Dados Cadastrais",
              style: TextStyle(
                color: HexColor("1a6069"),
              )),
          actions: <Widget>[
            IconButton(
              icon: const Icon(FontAwesomeIcons.bell),
              tooltip: 'Show Snackbar',
              color: HexColor("1a6069"),
              onPressed: () {
                ScaffoldMessenger.of(context).showSnackBar(
                    const SnackBar(content: Text('Notifications')));
              },
            ),
          ],
        ),
        body: SingleChildScrollView(
          child: Container(
            height: 100.h,
            child: Column(
              children: [
                Container(
                  height: 5.h,
                ),
                Container(
                  height: 80.h,
                  child: Column(
                    children: [
                      Image.asset(
                        "assets/images/person.png",
                        height: 15.h,
                      ),
                      SizedBox(
                        height: 4.h,
                      ),
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
                                  errorStyle:
                                      const TextStyle(color: Colors.white),
                                  prefixIcon: Container(
                                      padding: const EdgeInsets.only(
                                          top: 5.0, bottom: 5.0),
                                      margin: const EdgeInsets.only(right: 5.0),
                                      decoration: const BoxDecoration(
                                          color: Colors.white,
                                          borderRadius: BorderRadius.only(
                                              topLeft: Radius.circular(10.0),
                                              bottomLeft: Radius.circular(10.0),
                                              topRight: Radius.circular(0.0),
                                              bottomRight:
                                                  Radius.circular(0.0))),
                                      child: Icon(
                                        FontAwesomeIcons.userAlt,
                                        color: Colors.grey,
                                        size: 3.h,
                                      )),
                                  hintText: "Nome Completo",
                                  hintStyle: TextStyle(
                                      fontSize: 11.sp,
                                      color: Colors.grey.withOpacity(0.5)),
                                  border: OutlineInputBorder(
                                      borderRadius: BorderRadius.circular(10.0),
                                      borderSide: BorderSide.none),
                                  filled: true,
                                  fillColor: Colors.white,
                                ),
                                //style: TextStyle(fontSize: 11.sp, color: Colors.grey.withOpacity(0.1)),
                                obscureText: false,
                              ),
                            ),
                            SizedBox(
                              height: 2.h,
                            ),
                            SizedBox(
                              height: 6.h,
                              child: TextFormField(
                                keyboardType: TextInputType.text,
                                textInputAction: TextInputAction.done,
                                decoration: InputDecoration(
                                  contentPadding: const EdgeInsets.all(5.0),
                                  errorStyle:
                                      const TextStyle(color: Colors.white),
                                  prefixIcon: Container(
                                      padding: const EdgeInsets.only(
                                          top: 5.0, bottom: 5.0),
                                      margin: const EdgeInsets.only(right: 5.0),
                                      decoration: const BoxDecoration(
                                          color: Colors.white,
                                          borderRadius: BorderRadius.only(
                                              topLeft: Radius.circular(10.0),
                                              bottomLeft: Radius.circular(10.0),
                                              topRight: Radius.circular(0.0),
                                              bottomRight:
                                                  Radius.circular(0.0))),
                                      child: Icon(
                                        FontAwesomeIcons.idCard,
                                        color: Colors.grey,
                                        size: 3.h,
                                      )),
                                  hintText: "CPF",
                                  hintStyle: TextStyle(
                                      fontSize: 11.sp,
                                      color: Colors.grey.withOpacity(0.5)),
                                  border: OutlineInputBorder(
                                      borderRadius: BorderRadius.circular(10.0),
                                      borderSide: BorderSide.none),
                                  filled: true,
                                  fillColor: Colors.white,
                                ),
                                // style: TextStyle(fontSize: 11.sp, color: Colors.grey.withOpacity(0.3)),
                                obscureText: false,
                              ),
                            ),
                            SizedBox(
                              height: 2.h,
                            ),
                            SizedBox(
                              height: 6.h,
                              child: TextFormField(
                                keyboardType: TextInputType.text,
                                textInputAction: TextInputAction.done,
                                decoration: InputDecoration(
                                  contentPadding: const EdgeInsets.all(5.0),
                                  errorStyle: TextStyle(color: Colors.white),
                                  prefixIcon: Container(
                                      padding: const EdgeInsets.only(
                                          top: 5.0, bottom: 5.0),
                                      margin: const EdgeInsets.only(right: 5.0),
                                      decoration: const BoxDecoration(
                                          color: Colors.white,
                                          borderRadius: BorderRadius.only(
                                              topLeft: Radius.circular(10.0),
                                              bottomLeft: Radius.circular(10.0),
                                              topRight: Radius.circular(0.0),
                                              bottomRight:
                                                  Radius.circular(0.0))),
                                      child: Icon(
                                        FontAwesomeIcons.calendarAlt,
                                        color: Colors.grey,
                                        size: 3.h,
                                      )),
                                  hintText: "Data de Nascimento",
                                  hintStyle: TextStyle(
                                      fontSize: 11.sp,
                                      color: Colors.grey.withOpacity(0.5)),
                                  border: OutlineInputBorder(
                                      borderRadius: BorderRadius.circular(10.0),
                                      borderSide: BorderSide.none),
                                  filled: true,
                                  fillColor: Colors.white,
                                ),
                                // style: TextStyle(fontSize: 11.sp, color: Colors.grey.withOpacity(0.3)),
                                obscureText: false,
                              ),
                            ),
                            SizedBox(
                              height: 2.h,
                            ),
                            SizedBox(
                              height: 6.h,
                              child: TextFormField(
                                keyboardType: TextInputType.text,
                                textInputAction: TextInputAction.done,
                                decoration: InputDecoration(
                                  contentPadding: const EdgeInsets.all(5.0),
                                  errorStyle:
                                      const TextStyle(color: Colors.white),
                                  prefixIcon: Container(
                                      padding: const EdgeInsets.only(
                                          top: 5.0, bottom: 5.0),
                                      margin: const EdgeInsets.only(right: 5.0),
                                      decoration: const BoxDecoration(
                                          color: Colors.white,
                                          borderRadius: BorderRadius.only(
                                              topLeft: Radius.circular(10.0),
                                              bottomLeft: Radius.circular(10.0),
                                              topRight: Radius.circular(0.0),
                                              bottomRight:
                                                  Radius.circular(0.0))),
                                      child: Icon(
                                        FontAwesomeIcons.phone,
                                        color: Colors.grey,
                                        size: 3.h,
                                      )),
                                  hintText: "Celular",
                                  hintStyle: TextStyle(
                                      fontSize: 11.sp,
                                      color: Colors.grey.withOpacity(0.5)),
                                  border: OutlineInputBorder(
                                      borderRadius: BorderRadius.circular(10.0),
                                      borderSide: BorderSide.none),
                                  filled: true,
                                  fillColor: Colors.white,
                                ),
                                // style: TextStyle(fontSize: 11.sp, color: Colors.grey.withOpacity(0.3)),
                                obscureText: false,
                              ),
                            ),
                            SizedBox(
                              height: 2.h,
                            ),
                            SizedBox(
                              height: 6.h,
                              child: TextFormField(
                                keyboardType: TextInputType.text,
                                textInputAction: TextInputAction.done,
                                decoration: InputDecoration(
                                  contentPadding: const EdgeInsets.all(5.0),
                                  errorStyle:
                                      const TextStyle(color: Colors.white),
                                  prefixIcon: Container(
                                      padding: const EdgeInsets.only(
                                          top: 5.0, bottom: 5.0),
                                      margin: const EdgeInsets.only(right: 5.0),
                                      decoration: const BoxDecoration(
                                          color: Colors.white,
                                          borderRadius: BorderRadius.only(
                                              topLeft: Radius.circular(10.0),
                                              bottomLeft: Radius.circular(10.0),
                                              topRight: Radius.circular(0.0),
                                              bottomRight:
                                                  Radius.circular(0.0))),
                                      child: Icon(
                                        FontAwesomeIcons.envelope,
                                        color: Colors.grey,
                                        size: 3.h,
                                      )),
                                  hintText: "Email",
                                  hintStyle: TextStyle(
                                      fontSize: 11.sp,
                                      color: Colors.grey.withOpacity(0.5)),
                                  border: OutlineInputBorder(
                                      borderRadius: BorderRadius.circular(10.0),
                                      borderSide: BorderSide.none),
                                  filled: true,
                                  fillColor: Colors.white,
                                ),
                                // style: TextStyle(fontSize: 11.sp, color: Colors.grey.withOpacity(0.3)),
                                obscureText: false,
                              ),
                            )
                          ],
                        ),
                      ),
                      SizedBox(
                        height: 5.h,
                      ),
                      ElevatedButton(
                        onPressed: () {
                          Navigator.pop(context);
                        },
                        child: Container(
                          width: 75.w,
                          height: 6.h,
                          child: const Center(
                            child: Text("Alterar Dados"),
                          ),
                        ),
                        style: ElevatedButton.styleFrom(
                          primary: HexColor("1a6069"),
                          shape: RoundedRectangleBorder(
                            borderRadius:
                                BorderRadius.circular(12), // <-- Radius
                          ),
                        ),
                      ),
                      SizedBox(
                        height: 1.h,
                      ),
                      GestureDetector(
                        onTap: () {
                          Navigator.pop(context);
                        },
                        child: Text(
                          "Voltar",
                          style: TextStyle(
                              fontSize: 10.sp, color: HexColor("1a6069")),
                        ),
                      )
                    ],
                  ),
                )
              ],
            ),
          ),
        ));
  }
}
