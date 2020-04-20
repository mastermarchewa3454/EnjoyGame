using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using TMPro;

public class Login : MonoBehaviour
{
    private TMP_InputField usernameField;
    private TMP_InputField passwordField;

    private DBUserManager db;
    private MainMenu mm;

    private TMP_Text errorMsg;

    void Start()
    {
        db = GetComponent<DBUserManager>();
        mm = FindObjectOfType<MainMenu>();

        usernameField = transform.Find("Username").GetComponent<TMP_InputField>();
        passwordField = transform.Find("Password").GetComponent<TMP_InputField>();
        errorMsg = transform.Find("Error Message").GetComponent<TMP_Text>();

        PlayerPrefs.DeleteKey("authHeader");
        PlayerPrefs.DeleteKey("userId");
    }

    void LoginRedirect(int status)
    {
        Debug.Log("Status code : " + status.ToString());
        if (status == 200)
        {
            Debug.Log("Logging in...");
            if (PlayerPrefs.GetInt("teacher") == 0)
                mm.ReturnMainMenu();
            else
                mm.EnterTeacherScreen();
        }
        else
        {
            Debug.Log("Login failed");
            errorMsg.text = "You have entered an incorrect Login ID or Password. Please try again.";
        }
    }

    public void LoggingIn()
    {
        string username = usernameField.text;
        string password = passwordField.text;
        Debug.Log(username);
        Debug.Log(password);
        StartCoroutine(db.Login(username, password, LoginRedirect));
    }
}
