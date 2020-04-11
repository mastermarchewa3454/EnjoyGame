using System.Collections;
using System.Collections.Generic;
using UnityEngine;

/// <summary>
/// Script for handling player movement
/// </summary>
public class PlayerMovement : MonoBehaviour
{
    [SerializeField]
    private float moveSpeed = 5f;

    private Rigidbody2D rb;
    private Animator animator;

    Vector2 movement;
    Vector2 mousePos;

    /// <summary>
    /// Gets the relevant components
    /// </summary>
    void Start()
    {
        moveSpeed = PlayerPrefs.GetFloat("speed", moveSpeed);
        rb = gameObject.GetComponent<Rigidbody2D>();
        animator = gameObject.GetComponent<Animator>();
    }

    /// <summary>
    /// Gets player movement inputs
    /// </summary>
    void Update()
    {

        movement.x = Input.GetAxisRaw("Horizontal");
        movement.y = Input.GetAxisRaw("Vertical");

        animator.SetFloat("Horizontal", movement.x);
        animator.SetFloat("Vertical", movement.y);
        animator.SetFloat("Speed", movement.sqrMagnitude);
    }

    /// <summary>
    /// Moves the player
    /// </summary>
    void FixedUpdate()
    {
        rb.MovePosition(rb.position + movement * moveSpeed * Time.fixedDeltaTime);
    }





}

