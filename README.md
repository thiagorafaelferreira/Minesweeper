# Minesweeper
## This project is a backend for a classical game called minesweeper

Steps to create project
=================
<!--ts-->
   * Identify how game works
   * Create the main architecture
        * Java 11
        * Spring boot
        * Memory Database H2
        * Jacoco test coverage
        * Lombok reduce boilerplate
        * Organization using Hexagonal Architecture
        * Spring security (Not working properly)
        * Endpoints REST  
        * Swagger 
   * Problem solving
      * Understanding of the problem
      * Logical
      * Recursivity
<!--te-->

To build the mine field is necessary use the matrix.
Each positon in this matrix represents a postion on the field.
Each field has atributs that indicates if that field has mine, was flaged,
was clicked and has mine arround.

possible actions are:
new_game - start the game, you can define number of rows, columns and mines on the field
flag_positon - you put a flag in that position
click_position - you click in that position and there is no mine arround you clear that area until arrive near a mine.


Exemple field where zero is just position on field and 9 position of mine: \
\
0 0 0 0 0 0 0 0 0 0 \
0 0 0 0 0 0 0 0 0 0 \
0 0 0 0 0 0 0 0 0 0 \
9 0 0 0 0 0 0 0 0 0 \
0 0 0 0 0 0 0 9 0 0 \
0 0 0 0 0 0 0 0 0 0 \
0 0 0 0 0 0 0 0 0 0 \
0 0 0 0 0 9 0 0 0 0 \
0 0 0 0 0 0 0 0 0 0 \
0 0 0 0 0 0 0 0 0 0 


Access the swagger localy:
http://localhost:8080/swagger-ui/#/