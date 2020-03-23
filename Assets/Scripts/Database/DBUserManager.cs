using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;
using TMPro;
using UnityEngine.Networking;

public class DBUserManager : DBManager
{
    public TMP_Dropdown dropdownMenu;
    public TMP_InputField email;
    public TMP_InputField usernameField;
    public TMP_InputField passwordField;
    public TMP_InputField firstNameField;
    public TMP_InputField lastNameField;
    public Button registerButton;

    /// <summary>
    /// FOR TESTING PURPOSE
    /// </summary>
    void Update()
    {
        if (Input.GetKeyDown(KeyCode.R))
        {
            StartCoroutine(Register("Booxworm",
                                    "Wonn Jen",
                                    "Lee",
                                    "4C",
                                    "sth@gmail.com",
                                    "p@ssw0rd"));
        }
        if (Input.GetKeyDown(KeyCode.L))
        {
            StartCoroutine(Login("Booxworm", "p@ssw0rd"));
        }
        else if (Input.GetKeyDown(KeyCode.G))
        {
            StartCoroutine(GetUser());
        }
        else if (Input.GetKeyDown(KeyCode.Space))
        {
            Debug.Log(authHeader);
            Debug.Log(userId);
        }
    }

    public void CallRegister()
    {
        //get the selected index
        int menuIndex = dropdownMenu.value;
        //get all options available within this dropdown menu
        List<TMP_Dropdown.OptionData> menuOptions = dropdownMenu.options;
        //get the string value of the selected index
        string classValue = menuOptions[menuIndex].text;

        StartCoroutine(Register(usernameField.text,
                                firstNameField.text,
                                lastNameField.text,
                                classValue,
                                email.text,
                                passwordField.text));
    }

    public IEnumerator Register(string username,
                        string firstName,
                        string lastName,
                        string classId,
                        string email,
                        string password)
    {
        string userDetails = "{\"username\":\"" + username + "\"," +
                              "\"firstName\":\"" + firstName + "\"," +
                              "\"lastName\":\"" + lastName + "\"," +
                              "\"classId\":\"" + classId + "\"," +
                              "\"email\":\"" + email + "\"," +
                              "\"password\":\"" + password + "\"}";

        yield return StartCoroutine(PostData("/users", userDetails));
        Debug.Log("Registered!");
    }

    public IEnumerator Login(string username, string password)
    {
        string loginDetails = "{\"username\":\"" + username + "\"," +
                               "\"password\":\"" + password + "\"}";
        yield return StartCoroutine(PostData("/users/login", loginDetails, setAuth: true));
        Debug.Log("Logged in!");
    }

    public IEnumerator GetUser()
    {
        if (userId == null)
            Debug.Log("Log in first");
        else
        {
            string userString = "";
            yield return StartCoroutine(GetData("/users/" + userId, callback: data => userString = data));
            Student student = JsonUtility.FromJson<Student>(userString);
            Debug.Log(student.userId);
            Debug.Log(student.username);
            Debug.Log(student.firstName);
            Debug.Log(student.lastName);
            Debug.Log(student.email);
        }
    }

    /// <summary>
    /// This is to check the inputs for nameField and passwordField
    /// If they satisfy the conditions, then the button will be clickable. 
    /// Otherwise, the submit button will not be interactable.
    /// Something like a backend check
    /// </summary>
    public void VerifyInputs()
    {
        registerButton.interactable = (usernameField.text.Length >= 8 && passwordField.text.Length >= 8);
    }
}

public class Student
{
    public string userId;
    public string username;
    public string firstName;
    public string lastName;
    public string email;
}
