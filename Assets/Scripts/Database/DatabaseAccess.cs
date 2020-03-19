using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;
using TMPro;
using UnityEngine.Networking;

public class DatabaseAccess : MonoBehaviour
{
    public TMP_Dropdown dropdownMenu;
    public TMP_InputField email;
    public TMP_InputField usernameField;
    public TMP_InputField passwordField;
    public TMP_InputField firstNameField;
    public TMP_InputField lastNameField;
    public Button registerButton;


    public void CallRegister()
    {
        StartCoroutine(Register());
    }

    IEnumerator Register()
    {
        //get the selected index
        int menuIndex = dropdownMenu.value;
        //get all options available within this dropdown menu
        List<TMP_Dropdown.OptionData> menuOptions = dropdownMenu.options;
        //get the string value of the selected index
        string value = menuOptions[menuIndex].text;


        WWWForm form = new WWWForm(); ///creation of an empty form
        form.AddField("class", value);
        form.AddField("email", email.text);
        form.AddField("firstName", firstNameField.text);
        form.AddField("lastName", lastNameField.text);
        form.AddField("userName", usernameField.text);
        form.AddField("password", passwordField.text);
        UnityWebRequest www = UnityWebRequest.Get("http://13.229.205.106:8080/mathero/users");
        yield return www.SendWebRequest();
        if (www.isNetworkError || www.isHttpError)
        {
            Debug.Log("User Creation failed. Error #" + www.error);
        }
        else
        {
            Debug.Log("User created successfully.");
            //SceneManager.LoadScene(0) Include this if you want to load a certain scene after registering acc.
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
