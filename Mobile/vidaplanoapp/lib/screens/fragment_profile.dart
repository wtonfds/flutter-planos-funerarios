import 'package:flutter/material.dart';
import 'package:hexcolor/hexcolor.dart';
import 'package:sizer/sizer.dart';
import 'package:vidaplanoapp/screens/dependents_screen.dart';
import 'package:vidaplanoapp/screens/new_dependent_screen.dart';
import 'package:vidaplanoapp/screens/person_data_screen.dart';

class FragmentProfile extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return Container(
      child: Center(
        child: Column(
          children: [
            SizedBox(
              height: 8.h,
            ),
            Text("Plano Inativo", style: TextStyle(fontSize: 20.sp, color: Colors.red, fontWeight: FontWeight.w500),),
            SizedBox(
              height: 1.h,
            ),
            Text("Gerar Novo Boleto", style: TextStyle(fontSize: 10.sp, color: HexColor("1a6069"), fontWeight: FontWeight.w500)),
            SizedBox(
              height: 8.h,
            ),
            Text("Fatura em Atraso", style: TextStyle(fontSize: 12.sp, color: Colors.black, fontWeight: FontWeight.w500)),
            SizedBox(
              height: 10.h,
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.push(context, MaterialPageRoute(builder: (context) => NewDependentScreen()));
              },
              child: Container(
                width: 75.w,
                height: 6.h,
                child: Center(
                  child: Text("Dependentes", style: TextStyle(fontSize: 13.sp),),
                ),
              ),
              style: ElevatedButton.styleFrom(
                primary: HexColor("1a6069"),
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(12), // <-- Radius
                ),
              ),
            ),
            SizedBox(
              height: 3.h,
            ),
            ElevatedButton(
              onPressed: () {
                
              },
              child: Container(
                width: 75.w,
                height: 6.h,
                child: Center(
                  child: Text("Contrato", style: TextStyle(fontSize: 13.sp),),
                ),
              ),
              style: ElevatedButton.styleFrom(
                primary: HexColor("1a6069"),
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(12), // <-- Radius
                ),
              ),
            ),
            SizedBox(
              height: 3.h,
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.push(context, MaterialPageRoute(builder: (context) => PersonDataScreen()));
              },
              child: Container(
                width: 75.w,
                height: 6.h,
                child: Center(
                  child: Text("Dados Cadastrais", style: TextStyle(fontSize: 13.sp),),
                ),
              ),
              style: ElevatedButton.styleFrom(
                primary: HexColor("1a6069"),
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(12), // <-- Radius
                ),
              ),
            ),
            SizedBox(
              height: 3.h,
            ),
            ElevatedButton(
              onPressed: () {
                
              },
              child: Container(
                width: 75.w,
                height: 6.h,
                child: Center(
                  child: Text("Alterar Senha", style: TextStyle(fontSize: 13.sp),),
                ),
              ),
              style: ElevatedButton.styleFrom(
                primary: HexColor("1a6069"),
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(12), // <-- Radius
                ),
              ),
            ),
            SizedBox(
              height: 3.h,
            )
          ],
        ),
      )
    );
  }
}