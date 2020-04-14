using Photon.Pun;
using Photon.Realtime;
using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;

public class LobbyController : MonoBehaviourPunCallbacks
{
    // Start is called before the first frame update
    public static LobbyController lobby;
    public GameObject enterRoomButton;
    public TextMeshProUGUI waitingTextJoinLobby;
    public TextMeshProUGUI lobbyIDJoinLobby;
    public TextMeshProUGUI username;


    public GameObject searchButton;
    public GameObject cancelButton;
    public GameObject playButton;
    public TextMeshProUGUI waitingTextCreateLobby;
    public TextMeshProUGUI lobbyIDCreateLobby;
    private void Awake()
    {
        lobby = this;
    }
    void Start()
    {
        PhotonNetwork.ConnectUsingSettings(); // Connects to server
        waitingTextJoinLobby.SetText("");
        lobbyIDJoinLobby.SetText("");
        username.SetText("");
        enterRoomButton.SetActive(false);

        
        waitingTextCreateLobby.SetText("");
        playButton.SetActive(false);
        searchButton.SetActive(false);
        cancelButton.SetActive(false);
    }

    public override void OnConnectedToMaster()
    {
        Debug.Log("User has connected to Photon Server ");
        PhotonNetwork.AutomaticallySyncScene = true;
        enterRoomButton.SetActive(true);
        searchButton.SetActive(true);
    }

    public void OnEnterRoomButtonClick()
    {
        if (lobbyIDJoinLobby.GetParsedText().Equals(""))
        {
            waitingTextJoinLobby.SetText("Write lobbyID");
        }
        else if (username.GetParsedText().Equals(""))
        {
            waitingTextJoinLobby.SetText("Write username");
        }
        else
        {
            string roomID = lobbyIDJoinLobby.GetParsedText();
            string user = username.GetParsedText();
            waitingTextJoinLobby.SetText("Waiting...");
            PhotonNetwork.NickName = user;
            Debug.Log("The roomID is " + roomID);
            Debug.Log("The username is: " + user);
            Debug.Log("num of rooms: " + PhotonNetwork.CountOfRooms);
            PhotonNetwork.JoinRandomRoom();
        }
    }
    public void OnBackButtonClickJoinLobby()
    {
        if (PhotonNetwork.IsConnected)
        {
            PhotonNetwork.LeaveRoom();
        }
        setToSingleMode();
    }
    void setToSingleMode()
    {
        Spawner.isDuoMode = false;
        PlayerMovement.isDuoMode = false;
        FireController.isDuoMode = false;
        Health.isDuoMode = false;
        SceneChanger.isDuoMode = false;
        NextLevelDoor.isDuoMode = false;
    }

    public override void OnJoinedRoom()
    {
        Debug.Log("We are now in the room");
        waitingTextJoinLobby.SetText("Connected to room" + lobbyIDJoinLobby.GetParsedText());
        enterRoomButton.SetActive(false);
    }
    public override void OnJoinRoomFailed(short returnCode, string message)
    {
        Debug.Log("The room does not exist " + message);
        waitingTextJoinLobby.SetText("The room does not exist");
        Debug.Log("Num of rooms " + PhotonNetwork.CountOfRooms);
    }


    // For creating lobby

    public void OnSearchButtonClick()
    {
        searchButton.SetActive(false);
        cancelButton.SetActive(true);
        waitingTextCreateLobby.SetText("Waiting...");
        CreateRoom();
    }

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

    public override void OnPlayerEnteredRoom(Player newPlayer)
    {
        waitingTextCreateLobby.SetText("Connected to " + newPlayer.NickName);
        cancelButton.SetActive(false);
    }
    public override void OnCreatedRoom()
    {
        Debug.Log("New room has been created");
    }
    public override void OnCreateRoomFailed(short returnCode, string message)
    {
        Debug.Log("The room already exists");
        CreateRoom();
    }

    public void OnCancelButtonClick()
    {
        cancelButton.SetActive(false);
        searchButton.SetActive(true);
        waitingTextCreateLobby.SetText("");
        lobbyIDCreateLobby.SetText("LOBBY ID: <>");
        PhotonNetwork.LeaveRoom();
    }

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
