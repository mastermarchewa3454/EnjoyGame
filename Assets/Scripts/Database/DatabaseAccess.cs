using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

public class DatabaseAccess : MonoBehaviour
{
    public InputField nameField;
    public InputField passwordField;

    public Button submitButton;

    public void CallRegister()
    {
        StartCoroutine(Register());
    }

    IEnumerator Register()
    {
        WWWForm form = new WWWForm();
        form.AddField("name", nameField.text);
        form.AddField("password", passwordField.text);
        WWW www = new WWW("http://13.229.205.106:8080/mathero");
        yield return www;
        if (www.text == "0")
        {
            Debug.Log("User created successfully.");
            //SceneManager.LoadScene(0) Include this if you want to load a certain scene after registering acc.
        }
        else
        {
            Debug.Log("User Creation failed. Error #" + www.text);
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
        submitButton.interactable = (nameField.text.Length >= 8 && passwordField.text.Length >= 8);
    }
}
