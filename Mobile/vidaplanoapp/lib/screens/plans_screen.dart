import 'package:carousel_slider/carousel_slider.dart';
import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:hexcolor/hexcolor.dart';
import 'package:sizer/sizer.dart';
import 'package:vidaplanoapp/data/api_plans.dart';
import 'package:vidaplanoapp/models/plan.dart';
import 'package:vidaplanoapp/screens/payment_screen.dart';
import 'package:vidaplanoapp/screens/sign_up_complete_screen.dart';

class PlansScreen extends StatefulWidget {
  @override
  _PlansScreenState createState() => _PlansScreenState();
}

class _PlansScreenState extends State<PlansScreen> {
  List<Plan> plansList = [];
  ApiPlans apiPlans = ApiPlans();

  @override
  void initState() {
    super.initState();
    apiPlans.sendData();
  }

  // final List<Plan> plansList = [
  //   Plan(title: "Plano 01", description: "Descontos em consultas, exames, diagnóstico por imagem e farmácias em todo o Brasil.", value: 312.50),
  //   Plan(title: "Plano 02", description: "Descontos em consultas, exames, diagnóstico por imagem e farmácias em todo o Brasil.", value: 120.70),
  //   Plan(title: "Plano 03", description: "Descontos em consultas, exames, diagnóstico por imagem e farmácias em todo o Brasil.", value: 1000.80),
  //   Plan(title: "Plano 04", description: "Descontos em consultas, exames, diagnóstico por imagem e farmácias em todo o Brasil.", value: 312.50),
  //   Plan(title: "Plano 05", description: "Descontos em consultas, exames, diagnóstico por imagem e farmácias em todo o Brasil.", value: 120.70),
  // ];

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
                  height: 95.h,
                  child: Column(
                    children: [
                      Image.asset(
                        "assets/images/logo_transparent.png",
                        height: 15.h,
                      ),
                      SizedBox(
                        height: 4.h,
                      ),
                      Text(
                        "Escolha seu Plano",
                        style: TextStyle(
                            fontSize: 15.sp, color: HexColor("b63257")),
                      ),
                      SizedBox(
                        height: 0.h,
                      ),
                      plansList.isEmpty
                          ? _buildEmptyList()
                          : Container(
                              padding: EdgeInsets.only(left: 5.h),
                              width: 100.w,
                              height: 40.h,
                              child: SingleChildScrollView(
                                physics: const ScrollPhysics(),
                                child: Column(
                                  children: [
                                    ListView.separated(
                                      scrollDirection: Axis.vertical,
                                      shrinkWrap: true,
                                      physics: const BouncingScrollPhysics(),
                                      itemCount: plansList.length,
                                      itemBuilder: (context, index) {
                                        return _buildItemPlan(plansList[index]);
                                      },
                                      separatorBuilder:
                                          (BuildContext context, int index) {
                                        return Divider(
                                          height: 0.1.h,
                                        );
                                      },
                                    )
                                  ],
                                ),
                              ),
                            ),
                      SizedBox(
                        height: 3.h,
                      ),
                      plansList.isEmpty
                          ? const Center()
                          : Container(
                              width: 82.w,
                              height: 20.h,
                              child: Column(
                                children: [
                                  Row(
                                    children: [
                                      Container(
                                        width: 48.w,
                                        height: 6.h,
                                        child: TextFormField(
                                          keyboardType: TextInputType.text,
                                          textInputAction: TextInputAction.done,
                                          decoration: InputDecoration(
                                            contentPadding:
                                                const EdgeInsets.all(5.0),
                                            errorStyle: const TextStyle(
                                                color: Colors.white),
                                            hintText: "Cupom de Desconto",
                                            hintStyle: TextStyle(
                                                fontSize: 10.sp,
                                                color: Colors.grey
                                                    .withOpacity(0.2)),
                                            border: OutlineInputBorder(
                                                borderRadius:
                                                    BorderRadius.circular(10.0),
                                                borderSide: BorderSide.none),
                                            filled: true,
                                            fillColor: Colors.white,
                                          ),
                                          //style: TextStyle(fontSize: 11.sp, color: Colors.grey.withOpacity(0.1)),
                                          obscureText: false,
                                        ),
                                      ),
                                      SizedBox(
                                        width: 5.w,
                                      ),
                                      ElevatedButton(
                                        onPressed: () {
                                          //Navigator.push(context, MaterialPageRoute(builder: (context) => SignUpCompleteScreen()));
                                        },
                                        child: Container(
                                          width: 20.w,
                                          height: 4.h,
                                          child: const Center(
                                            child: Text("Aplicar"),
                                          ),
                                        ),
                                        style: ElevatedButton.styleFrom(
                                          primary: HexColor("1a6069"),
                                          shape: RoundedRectangleBorder(
                                            borderRadius: BorderRadius.circular(
                                                12), // <-- Radius
                                          ),
                                        ),
                                      ),
                                    ],
                                  ),
                                  SizedBox(
                                    height: 2.h,
                                  ),
                                  Text(
                                    "Valor Total",
                                    style: TextStyle(
                                        fontSize: 18.sp,
                                        color: HexColor("181461")),
                                  ),
                                  Text(
                                    "R\$ 0,00",
                                    style: TextStyle(
                                        fontSize: 15.sp,
                                        color: HexColor("181461")),
                                  ),
                                  SizedBox(
                                    height: 1.h,
                                  ),
                                  Text(
                                    "Visulizar Contrato",
                                    style: TextStyle(
                                        fontSize: 8.sp,
                                        color: HexColor("1a6069")),
                                  )
                                ],
                              ),
                            ),
                      plansList.isEmpty
                          ? ElevatedButton(
                              onPressed: () {
                                Navigator.pop(context);
                              },
                              child: Container(
                                width: 75.w,
                                height: 6.h,
                                child: const Center(
                                  child: Text("Voltar"),
                                ),
                              ),
                              style: ElevatedButton.styleFrom(
                                primary: HexColor("1a6069"),
                                shape: RoundedRectangleBorder(
                                  borderRadius:
                                      BorderRadius.circular(12), // <-- Radius
                                ),
                              ),
                            )
                          : ElevatedButton(
                              onPressed: () {
                                Navigator.push(
                                    context,
                                    MaterialPageRoute(
                                        builder: (context) => PaymentScreen()));
                              },
                              child: Container(
                                width: 75.w,
                                height: 6.h,
                                child: const Center(
                                  child: Text("Continuar"),
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
                    ],
                  ),
                )
              ],
            ),
          ),
        ));
  }

  Widget _buildItemPlan(Plan item) {
    return Card(
      elevation: 0,
      color: HexColor("#ecf1fa"),
      child: ExpansionTile(
        expandedAlignment: Alignment.center,
        trailing: const SizedBox.shrink(),
        title: Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Expanded(
              flex: 3,
              child: Text(
                item.title!,
                style: TextStyle(fontSize: 10.sp, fontWeight: FontWeight.w500),
              ),
            ),
            Expanded(flex: 4, child: Container()),
            Expanded(
                flex: 3,
                child: Align(
                  alignment: Alignment.centerLeft,
                  child: Text(
                    "R\$ ${item.value}",
                    style: TextStyle(
                        fontSize: 10.sp,
                        color: HexColor("1a6069"),
                        fontWeight: FontWeight.w500),
                  ),
                ))
          ],
        ),
        children: <Widget>[
          ListTile(
            title: Text(
              item.description!,
              style: TextStyle(fontSize: 10.sp),
            ),
          )
        ],
      ),
    );
  }

  Widget _buildEmptyList() {
    return Container(
      height: 40.0.h,
      alignment: Alignment.center,
      child: const Text("Não existem planos cadastrados."),
    );
  }
}
