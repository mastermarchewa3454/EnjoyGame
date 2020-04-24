using Photon.Pun;
using Photon.Realtime;
using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;

/// <summary>
/// Script for handling creating the lobby and joining the lobby.
/// </summary>
public class LobbyController : MonoBehaviourPunCallbacks
{
    public static LobbyController lobby;
    public GameObject enterRoomButton;
    public TextMeshProUGUI waitingTextJoinLobby;
    public TextMeshProUGUI lobbyIDJoinLobby;
    public TMP_Text leaderID;   // For create room lobby

    public GameObject searchButton;
    public GameObject cancelButton;
    public GameObject playButton;
    public TextMeshProUGUI waitingTextCreateLobby;
    public TextMeshProUGUI lobbyIDCreateLobby;

    string user;    // Stores username of current user
    DBUserManager db;

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
        db = GetComponent<DBUserManager>();
        StartCoroutine(db.GetUser(data =>
        {
            user = data.username;
            leaderID.text = user;
        }));

        PhotonNetwork.ConnectUsingSettings(); // Connects to server
        waitingTextJoinLobby.SetText("");
        lobbyIDJoinLobby.SetText("");
        enterRoomButton.SetActive(false);

        
        waitingTextCreateLobby.SetText("");
        playButton.SetActive(false);
        searchButton.SetActive(false);
        cancelButton.SetActive(false);
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
        searchButton.SetActive(true);
    }
    /// <summary>
    /// When user clicks Enter room button.
    /// </summary>
    public void OnEnterRoomButtonClick()
    {
        if (lobbyIDJoinLobby.GetParsedText().Equals(""))
        {
            waitingTextJoinLobby.SetText("Write lobbyID");
        }
        else
        {
            string roomID = lobbyIDJoinLobby.GetParsedText();
            waitingTextJoinLobby.SetText("Waiting...");
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
    public void OnBackButtonClickJoinLobby()
    {
        if (PhotonNetwork.IsConnected)
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
        SceneChanger.isDuoMode = false;
        NextLevelDoor.isDuoMode = false;
    }

    /// <summary>
    /// Display the text when other user join the room.
    /// </summary>
    public override void OnJoinedRoom()
    {
        Debug.Log("We are now in the room");
        waitingTextJoinLobby.SetText("Connected to room" + lobbyIDJoinLobby.GetParsedText());
        enterRoomButton.SetActive(false);
    }

    /// <summary>
    /// Display the text when player tries to join the roomw which does not exist.
    /// </summary>
    /// <param name="returnCode"></param>
    /// <param name="message"></param>
    public override void OnJoinRoomFailed(short returnCode, string message)
    {
        Debug.Log("The room does not exist " + message);
        waitingTextJoinLobby.SetText("The room does not exist");
        Debug.Log("Num of rooms " + PhotonNetwork.CountOfRooms);
    }


    // For creating lobby

    /// <summary>
    /// When user clicks Search room button.
    /// </summary>
    public void OnSearchButtonClick()
    {
        searchButton.SetActive(false);
        cancelButton.SetActive(true);
        waitingTextCreateLobby.SetText("Waiting...");
        CreateRoom();
    }

    /// <summary>
    /// Create Room and set parameter of this room.
    /// </summary>
    void CreateRoom()
    {
        PhotonNetwork.NickName = "Hans";
        Debug.Log("Trying to create a new room");
        int roomID = Random.Range(0, 1000);
        RoomOptions roomOptions = new RoomOptions()
        {
            IsVisible = true,
            IsOpen = true,
            MaxPlayers = 2
        };
        lobbyIDCreateLobby.SetText("LOBBY ID: " + roomID.ToString());
        PhotonNetwork.CreateRoom("Room" + roomID, roomOptions);
    }

    /// <summary>
    /// When other player enter the room.
    /// </summary>
    /// <param name="newPlayer"></param>
    public override void OnPlayerEnteredRoom(Player newPlayer)
    {
        waitingTextCreateLobby.SetText("Connected to " + newPlayer.NickName);
        cancelButton.SetActive(false);
    }

    /// <summary>
    /// Signal when new room is created.
    /// </summary>
    public override void OnCreatedRoom()
    {
        Debug.Log("New room has been created");
    }

    /// <summary>
    /// Signal when there is an error about creating new room.
    /// </summary>
    /// <param name="returnCode"></param>
    /// <param name="message"></param>
    public override void OnCreateRoomFailed(short returnCode, string message)
    {
        Debug.Log("The room already exists");
        CreateRoom();
    }

    /// <summary>
    /// When user clicks Cancel button.
    /// </summary>
    public void OnCancelButtonClick()
    {
        cancelButton.SetActive(false);
        searchButton.SetActive(true);
        waitingTextCreateLobby.SetText("");
        lobbyIDCreateLobby.SetText("LOBBY ID: <>");
        PhotonNetwork.LeaveRoom();
    }

    /// <summary>
    /// When user clicks Back button in Create Lobby.
    /// </summary>
    public void OnBackButtonClickCreateLobby()
    {
        if (PhotonNetwork.IsConnected)
        {
            PhotonNetwork.LeaveRoom();
        }
        if (PhotonRoom.theRoom != null)
        {
            Destroy(PhotonRoom.theRoom.gameObject);
        }
        setToSingleMode();
    }
}
