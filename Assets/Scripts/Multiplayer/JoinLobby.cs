using Photon.Pun;
using Photon.Realtime;
using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;

/// <summary>
/// Script for handling joining the lobby.
/// </summary>
public class JoinLobby : MonoBehaviourPunCallbacks
{
    // Start is called before the first frame update
    public static JoinLobby lobby;
    public GameObject enterRoomButton;
    public TextMeshProUGUI waitingText;
    public TextMeshProUGUI lobbyID;
    public TextMeshProUGUI username;

    /// <summary>
    /// Awake this lobby.
    /// </summary>
    private void Awake()
    {
        lobby = this;
    }

    /// <summary>
    /// Initialize buttons and text on the screen.
    /// Connect to server of Photon Network.
    /// </summary>
    void Start()
    {
        PhotonNetwork.ConnectUsingSettings(); // Connects to server
        waitingText.SetText("");
        lobbyID.SetText("");
        username.SetText("");
        enterRoomButton.SetActive(false);
    }

    /// <summary>
    /// Connect to network.
    /// Synchronize the screen for the players.
    /// </summary>
    public override void OnConnectedToMaster()
    {
        Debug.Log("User has connected to Photon Server ");
        PhotonNetwork.AutomaticallySyncScene = true;
        enterRoomButton.SetActive(true);
    }

    /// <summary>
    /// When user clicks Enter room button.
    /// </summary>
    public void OnEnterRoomButtonClick()
    {
        if(lobbyID.GetParsedText().Equals(""))
        {
            waitingText.SetText("Write lobbyID");
        }
        else if(username.GetParsedText().Equals(""))
        {
            waitingText.SetText("Write username");
        }
        else
        {
            string roomID = lobbyID.GetParsedText();
            string user = username.GetParsedText();
            waitingText.SetText("Waiting...");
            PhotonNetwork.NickName = user;
            Debug.Log("The roomID is " + roomID);
            Debug.Log("The username is: " + user);
            Debug.Log("num of rooms: " + PhotonNetwork.CountOfRooms);
            PhotonNetwork.JoinRandomRoom();
        }        
    }

    /// <summary>
    /// When user clicks Back room button.
    /// </summary>
    public void OnBackButtonClick()
    {
        if(PhotonNetwork.IsConnected)
        {
            PhotonNetwork.LeaveRoom();
        }
        setToSingleMode();
    }

    /// <summary>
    /// Set the parameters in other class to play single mode.
    /// </summary>
    void setToSingleMode()
    {
        Spawner.isDuoMode = false;
        PlayerMovement.isDuoMode = false;
        FireController.isDuoMode = false;
        Health.isDuoMode = false;
    }

    /// <summary>
    /// Display the text when other user join the room.
    /// </summary>
    public override void OnJoinedRoom()
    {
        Debug.Log("We are now in the room");
        waitingText.SetText("Connected to room" + lobbyID.GetParsedText());
        enterRoomButton.SetActive(false);
    }

    /// <summary>
    /// Display the text when player tries to join the roomw which does not exist.
    /// </summary>
    /// <param name="returnCode"></param>
    /// <param name="message"></param>
    public override void OnJoinRoomFailed(short returnCode, string message)
    {
        base.OnJoinRoomFailed(returnCode, message);
        Debug.Log("The room does not exist " + message);
        waitingText.SetText("The room does not exist");
        Debug.Log("Num of rooms " + PhotonNetwork.CountOfRooms);
    }
}
