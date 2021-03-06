﻿using System.Collections;
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

    [SerializeField]
    private TMP_Text errorMsg;
    List<string> listUser;
    DBUserManager db;

    /// <summary>
    /// Start function that will be called upon entering the screen. It will contact the database and populate the summary page 
    /// with the relevant details that are necessary.
    /// </summary>
    void Start()
    {
        db = GetComponent<DBUserManager>();
        listUser = new List<string>();
        Debug.Log("Starting");
        StartCoroutine(db.GetUsers(callback: data =>
        {
            foreach (Student s in data)
            {
                listUser.Add(s.username + ":" + s.userId + ":" + s.firstName + ":" + s.className);
            }
        }));
    }
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
            errorMsg.text = "Please complete all fields.";
        }
        else
        {
            SearchByUserName(searchInput.text);
        }
    }

    /// <summary>
    /// A function to search the username of a user by going through database values and finding the corresponding username.
    /// </summary>
    /// <param name="otherUserName"> otherUsername is the text that is acquired from the inputfield in unity.</param>
    public void SearchByUserName(string otherUserName)
    {
        bool found =false;
        foreach (string user in listUser)
        {
            string[] identity = user.Split(':');
            if (identity[0].Equals(otherUserName))
            {
                found = true; 
                Debug.Log("Login Successful");
                PlayerPrefs.SetString("otherUserId", identity[1]);
                PlayerPrefs.SetString("firstName", identity[2]);
                PlayerPrefs.SetString("className", identity[3]);
                SceneManager.LoadScene("SummaryReportDetails");
            }
        }
        if(!found)
        {
            errorMsg.text = "You have entered an incorrect user name. Please try again.";
        }
    }
}
