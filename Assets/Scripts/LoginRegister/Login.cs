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

    void LoginRedirect(int status)
    {
        Debug.Log("Status code : " + status.ToString());
        if (status == 200)
        {
            Debug.Log("Logging in...");
            mm.ReturnMainMenu();
        }
        else
        {
            Debug.Log("Login failed");
        }
    }

    public void StudentLogin()
    {
        string username = usernameField.text;
        string password = passwordField.text;
        Debug.Log(username);
        Debug.Log(password);
        StartCoroutine(db.Login(username, password, LoginRedirect));
        if (PlayerPrefs.HasKey("Teacher"))
        {
            PlayerPrefs.SetInt("Teacher", 0);
            PlayerPrefs.Save();
            Debug.Log("Teacher key: " + PlayerPrefs.GetInt("Teacher"));
        }
        else
        {
            Debug.Log("PlayerPrefs doesn't exist.");
            PlayerPrefs.SetInt("Teacher", 0);
            Debug.Log("PlayerPrefs 'Teacher' created " + PlayerPrefs.GetInt("Teacher"));
        }
    }

    public void TeacherLogin()
    {
        string username = usernameField.text;
        string password = passwordField.text;
        Debug.Log(username);
        Debug.Log(password);
        StartCoroutine(db.Login(username, password, LoginRedirect));
        if(PlayerPrefs.HasKey("Teacher")){
            PlayerPrefs.SetInt("Teacher", 1);
            PlayerPrefs.Save();
            Debug.Log("Teacher key: " + PlayerPrefs.GetInt("Teacher"));
        }
        else{
            Debug.Log("PlayerPrefs doesn't exist.");
            PlayerPrefs.SetInt("Teacher", 1);
            Debug.Log("PlayerPrefs 'Teacher' created" + PlayerPrefs.GetInt("Teacher"));
        }
    }
}
