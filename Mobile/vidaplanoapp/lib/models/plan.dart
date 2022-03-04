class Plan {
  String? title;
  String? description;
  String? value;

  Plan({this.title, this.description, this.value});

  factory Plan.fromJson(Map<String, dynamic> json) {
    return Plan(
        title: json['title'],
        description: json['description'],
        value: json['value']);
  }
}
