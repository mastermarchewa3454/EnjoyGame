using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using TMPro;

public class Register : MonoBehaviour
{
    private TMP_Dropdown dropdownMenu;
    private TMP_InputField emailField;
    private TMP_InputField usernameField;
    private TMP_InputField passwordField;
    private TMP_InputField firstNameField;
    private TMP_InputField lastNameField;
    private TMP_Text errorMsg;
    private Button registerButton;

    DBUserManager db;
    MainMenu mm;

    private bool isClassSelected = false;
    private string classSelected;
    private string email;
    private string username;
    private string password; 

    private void Awake(){
        db = GetComponent<DBUserManager>();
        mm = FindObjectOfType<MainMenu>();

        dropdownMenu = transform.Find("Dropdown").GetComponent<TMP_Dropdown>();
        emailField = transform.Find("Email").GetComponent<TMP_InputField>();
        usernameField = transform.Find("Username").GetComponent<TMP_InputField>();
        passwordField = transform.Find("Password").GetComponent<TMP_InputField>();
        firstNameField = transform.Find("firstName").GetComponent<TMP_InputField>();
        lastNameField = transform.Find("lastName").GetComponent<TMP_InputField>();
        errorMsg = transform.Find("ErrorMsg").GetComponent<TMP_Text>();
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

    public void SubmitForm()
    {
        //get the selected index
        int menuIndex = dropdownMenu.value;
        //get all options available within this dropdown menu
        List<TMP_Dropdown.OptionData> menuOptions = dropdownMenu.options;
        //get the string value of the selected index
        string classValue = menuOptions[menuIndex].text;

        StartCoroutine(db.Register(usernameField.text,
                                   firstNameField.text,
                                   lastNameField.text,
                                   classValue,
                                   emailField.text,
                                   passwordField.text,
                                   data =>
                                   {
                                       if (data.Equals("success"))
                                       {
                                           mm.EnterLogIn();
                                       }
                                       else
                                       {
                                           errorMsg.text = data;
                                       }
                                   }));
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
