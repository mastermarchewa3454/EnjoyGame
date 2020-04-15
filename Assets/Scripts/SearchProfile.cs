using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;
using TMPro;

public class SearchProfile : MonoBehaviour
{
    /// <summary>
    /// InputField Unity variables to store the questions and answers that are being created by the user.
    /// </summary>
    [SerializeField]
    private TMP_InputField searchInput;

    /// <summary>
    /// Method to get the user name from the searchInput.
    /// </summary>
    public void getUserName()
    {
        Debug.Log("The profile you searched for is : " + searchInput.text);
    }

    /// <summary>
    /// Method to retrieve the username and enter the next scene. There is also an error handling method to ensure that the 
    /// user does not enter an empty string/array.
    /// </summary>
    public void OnMouseEnter()
    {
        bool searchBoolean = string.IsNullOrEmpty(searchInput.text);
        if (searchBoolean)
        {
            Debug.Log("Please complete all fields");
        }
    }
}
