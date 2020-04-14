package kt.common

val TEAM = listOf(
        "Venky Viswanathan",
        "Satya", // One word name
        "Anshul Rawat",
        "Manasa Ranjan Tripathi", // More than 2 word name
        "Sivaram Yadala",
        "Gopal  S  Akshintala", // All 3 words separated by more than one space
        "Ravi Shankar",
        "Manoj Kumar  Pendhyala", // Only One word in a 3 word name separated by more than one space
        "", // Empty - We are hiring! ;)
        "Manikanta Yakkala",
        "Muneer  Ahmed", // Two words separated by more than one space
        "Prateek Sharma",
        "Sowmya Tammana ", // last word with one space after last word
        "Srinivas Vemula  ", // last word with two spaces after last word
        "Himanshu Kapoor",
        " ", // Just one Space
        "   ", // Only space characters
        null // NULL
)

const val DELIMITER = " 🤝 ";

const val EXPECTED_RESULT = "Viswanathan 🤝 Satya 🤝 Rawat 🤝 Tripathi 🤝 Yadala 🤝 Akshintala 🤝 Shankar 🤝 Pendhyala 🤝 Yakkala 🤝 Ahmed 🤝 Sharma 🤝 Tammana 🤝 Vemula 🤝 Kapoor";
