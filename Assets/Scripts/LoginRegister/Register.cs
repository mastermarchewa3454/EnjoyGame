using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using TMPro;

public class Register : MonoBehaviour
{
    private TMP_InputField emailInput;
    private TMP_InputField usernameInput;
    private TMP_InputField passwordInput;
    private Button registerButton;

    private bool isClassSelected = false;
    private string classSelected;
    private string email;
    private string username;
    private string password; 

    private void Awake(){
        emailInput = transform.Find("Email").GetComponent<TMP_InputField>();
        usernameInput = transform.Find("Username").GetComponent<TMP_InputField>();
        passwordInput = transform.Find("Password").GetComponent<TMP_InputField>();
        registerButton = transform.Find("registerButton").GetComponent<Button>();
    }

    public void HandleInputData(int val)
    {
        switch(val){
            case 1:
            Debug.Log("Class A");
            isClassSelected = true;
            classSelected = "Class A";
            break;
            case 2:
            Debug.Log("Class B");
            isClassSelected = true;
            classSelected = "Class B";
            break;
            case 3:
            Debug.Log("Class C");
            isClassSelected = true;
            classSelected = "Class C";
            break;
            default:
            Debug.Log("Select Your Class");
            isClassSelected = false;
            classSelected = null;
            break;
            
        }
    }

    public void RegisterVerification(){}

}
