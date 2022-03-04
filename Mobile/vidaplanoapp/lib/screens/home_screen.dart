import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:hexcolor/hexcolor.dart';
import 'package:sizer/sizer.dart';
import 'package:vidaplanoapp/screens/fragment_appointments.dart';
import 'package:vidaplanoapp/screens/fragment_discount.dart';
import 'package:vidaplanoapp/screens/fragment_funenary.dart';
import 'package:vidaplanoapp/screens/fragment_home.dart';
import 'package:vidaplanoapp/screens/fragment_profile.dart';

class HomeScreen extends StatefulWidget {
  @override
  _HomeScreenState createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {

  int _selectedIndex = 0;
  List<String> pages = [
    "Home", "Consultas", "Descontos", "Funerária", "Perfil"
  ];

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: HexColor("#ecf1fa"),
      appBar: AppBar(
        backgroundColor: Colors.white,
        automaticallyImplyLeading: false,
        centerTitle: false,
        title: Text(pages[_selectedIndex], style: TextStyle(color: HexColor("1a6069"),)),
        actions: <Widget>[
          IconButton(
            icon: const Icon(FontAwesomeIcons.bell),
            tooltip: 'Show Snackbar',
            color: HexColor("1a6069"),
            onPressed: () {
              ScaffoldMessenger.of(context).showSnackBar(
                   SnackBar(content: Text('Notifications')));
            },
          ),
        ],
      ),
      bottomNavigationBar: BottomNavigationBar(
        items: <BottomNavigationBarItem>[
          BottomNavigationBarItem(
            icon: Icon(FontAwesomeIcons.home),
            label: 'Home',
            backgroundColor: HexColor("1a6069"),
          ),
          BottomNavigationBarItem(
            icon: Icon(FontAwesomeIcons.bookMedical),
            label: 'Consultas',
            backgroundColor: HexColor("1a6069"),
          ),
          BottomNavigationBarItem(
            icon: Icon(FontAwesomeIcons.percentage),
            label: 'Descontos',
            backgroundColor: HexColor("1a6069"),
          ),
          BottomNavigationBarItem(
            icon: Icon(FontAwesomeIcons.building),
            label: 'Funerária',
            backgroundColor: HexColor("1a6069"),
          ),
          BottomNavigationBarItem(
            icon: Icon(FontAwesomeIcons.user),
            label: 'Perfil',
            backgroundColor: HexColor("1a6069"),
          ),
        ],
        currentIndex: _selectedIndex,
        selectedItemColor: Colors.pink,
        onTap: _onItemTapped,
      ),
      body: newScreen(),
    );
  }

  newScreen(){
    switch (_selectedIndex) {
      case 0:
          return FragmentHome();
        break;
      case 1:
        return FragmentAppointments();
      break;
       case 2:
          return FragmentDiscount();
        break;
      case 3:
        return FragmentFunerary();
      break;
       case 4:
          return FragmentProfile();
        break;
      default:
    }
  }
}