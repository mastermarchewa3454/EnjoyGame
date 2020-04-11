using Photon.Pun;
using Photon.Realtime;
using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;

public class JoinLobby : MonoBehaviourPunCallbacks
{
    // Start is called before the first frame update
    public static JoinLobby lobby;
    public GameObject enterRoomButton;
    public TextMeshProUGUI waitingText;
    public TextMeshProUGUI lobbyID;
    public TextMeshProUGUI username;
    private void Awake()
    {
        lobby = this;
    }
    void Start()
    {
        PhotonNetwork.ConnectUsingSettings(); // Connects to server
        waitingText.SetText("");
        lobbyID.SetText("");
        username.SetText("");
    }

    public override void OnConnectedToMaster()
    {
        Debug.Log("User has connected to Photon Server ");
        PhotonNetwork.AutomaticallySyncScene = true;
    }
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
    public void OnBackButtonClick()
    {
        if(PhotonNetwork.IsConnected)
        {
            PhotonNetwork.LeaveRoom();
        }        
    }

    
    public override void OnJoinedRoom()
    {
        Debug.Log("We are now in the room");
        waitingText.SetText("Connected to room" + lobbyID.GetParsedText());
    }
    public override void OnJoinRoomFailed(short returnCode, string message)
    {
        base.OnJoinRoomFailed(returnCode, message);
        Debug.Log("The room does not exist " + message);
        waitingText.SetText("The room does not exist");
        Debug.Log("Num of rooms " + PhotonNetwork.CountOfRooms);
    }
    // Update is called once per frame
    void Update()
    {
        
    }
}
