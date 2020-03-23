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

    void Start()
    {
        db = GetComponent<DBUserManager>();
        mm = FindObjectOfType<MainMenu>();

        usernameField = transform.Find("Username").GetComponent<TMP_InputField>();
        passwordField = transform.Find("Password").GetComponent<TMP_InputField>();

        PlayerPrefs.DeleteKey("authHeader");
        PlayerPrefs.DeleteKey("userId");
    }

    public void UserLogin()
    {
        string username = usernameField.text;
        string password = passwordField.text;
        Debug.Log(username);
        Debug.Log(password);
        StartCoroutine(db.Login(username, password));
        mm.ReturnMainMenu();
    }
}
