﻿using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

/// <summary>
/// Contains the methods that the MainMenu holds and utilizes 
/// </summary>
public class MainMenu : MonoBehaviour
{


    public void Start()
    {
        string userId = PlayerPrefs.GetString("userId", null);
        string authHeader = PlayerPrefs.GetString("authHeader", null);
        PlayerPrefs.DeleteAll();
        PlayerPrefs.SetString("userId", userId);
        PlayerPrefs.SetString("authHeader", authHeader);
    }

    /// <summary>
    /// Method call to Start the game
    /// </summary>
    public void PlayGame()
    {
        SceneManager.LoadScene("CharacterSelection");
    }

    /// <summary>
    /// Method call to view the leaderboard
    /// </summary>
    public void Leaderboard()
    {
        SceneManager.LoadScene("Leaderboard");
    }

    /// <summary>
    /// Method call to view the duo mode screen
    /// </summary>
    public void DuoMode()
    {
        SceneManager.LoadScene("DuoMode");
    }

    /// <summary>
    /// Method call to End the game.
    /// </summary>
    public void QuitGame()
    {
        Debug.Log("Exit Successful!");
        Application.Quit();
    }
    /// <summary>
    /// Method call to return to the main menu.
    /// </summary>
    public void ReturnMainMenu()
    {
        SceneManager.LoadScene("StartScreen");
    }
    /// <summary>
    /// Method call to enter the social menu of the game.
    /// </summary>
    public void EnterSocialMenu()
    {
        SceneManager.LoadScene("Social");
    }
    /// <summary>
    /// Method call to enter custom creation lobby to create a custom game.
    /// </summary>
    public void EnterCustomLobby()
    {
        SceneManager.LoadScene("QnA");
    }
    /// <summary>
    /// Method call to choose the class and students to view the report for the teacher.
    /// </summary>
    public void EnterTeacherReport()
    {
        SceneManager.LoadScene("TeacherReport");
    }
    /// <summary>
    /// Method call to enter the main menu screen for the teacher.
    /// </summary>
    public void EnterTeacherScreen()
    {
        SceneManager.LoadScene("TeacherScreen");
    }
    /// <summary>
    /// Method call to view the summary reports that will include 
    /// 1)Stages cleared
    /// 2)Questions answer 
    /// 3)Overall progress report
    /// </summary>
    public void EnterReportSummary()
    {
        SceneManager.LoadScene("SummaryReportDetails");
    }
    /// <summary>
    /// Method call to redirect the user back to the login page.
    /// </summary>
    public void EnterLogIn()
    {
        SceneManager.LoadScene("LoginScreen");
    }
    /// <summary>
    /// Method call to choose the class and students to view the report for the teacher.
    /// </summary>
    public void EnterCustomLobbyTeacherEdt()
    {
        PlayerPrefs.SetInt("Teacher", SceneManager.GetActiveScene().buildIndex);
        PlayerPrefs.Save();
        SceneManager.LoadScene("QnA");
    }

    /// <summary>
    /// Method call to redirect the user to the Custom Lobby screen.
    /// </summary>
    public void EnterCustomLobbyScreen()
    {
        SceneManager.LoadScene("CustomLobbyScreen");
    }

    /// <summary>
    /// Method call to redirect the user to the main menu page after clicking "Back" in Custom Lobby screen, depending on the user's role.
    ///</summary>
    public void LeaveFromCustomLobby()
    {
        if(PlayerPrefs.HasKey("Teacher")){
            int flag = PlayerPrefs.GetInt("Teacher");
            if(flag==1){
                EnterTeacherScreen();
            }
            else if (flag==0){
                ReturnMainMenu();
            }
        }
        else{
            ReturnMainMenu();
        }
    }
}
