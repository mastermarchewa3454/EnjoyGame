using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class EnemyMovement : MonoBehaviour
{
    public float speed = 1;
    public float movementInterval = 1;
    public float stopInterval = 2;

    public Rigidbody2D rb;
    Animator anim;

    Vector2 movement;
    bool moving = false;
    bool faceRight = false;

    void Start()
    {
        anim = GameObject.Find("Sprite").GetComponent<Animator>();
        StartCoroutine(NewHeading());
    }

    void FixedUpdate()
    {
        // Move sprite
        rb.MovePosition(rb.position + movement * speed * Time.fixedDeltaTime);
    }

    IEnumerator NewHeading()
    {
        while (this != null)
        {
            // Alternates between movement and non-movement phase
            moving = !moving;
            anim.SetBool("isMoving", moving);

            if (moving)
            {
                // Sets a new direction to head in
                NewHeadingRoutine();
                yield return new WaitForSeconds(movementInterval);
            }
            else
            {
                // Stops moving
                StopHeading();
                yield return new WaitForSeconds(stopInterval);
            }
        }
    }

    void StopHeading()
    {
        movement.x = 0;
        movement.y = 0;
    }

    void NewHeadingRoutine()
    {
        movement.x = Random.Range(-1f, 1f);
        movement.y = Random.Range(-1f, 1f);

        // Flip sprite if moving in opposite direction
        if ((movement.x > 0 && !faceRight) || (movement.x < 0 && faceRight))
        {
            rb.transform.localScale = new Vector2(rb.transform.localScale.x * -1, 1);
            faceRight = !faceRight;
        }
    }
}
