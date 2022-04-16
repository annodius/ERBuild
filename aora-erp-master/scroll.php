<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>Smooth Scroll jQuery Plugin Demo</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
    <script type="text/javascript" src="scripts/jquery.js"></script>
    <script type="text/javascript" src="scripts/smoothscroll.js"></script>
  <script>
    $(document).ready(function() {

      $('.container').smoothScroll({
        delegateSelector: 'ul.mainnav a'
      });

      $('p.subnav a').click(function(event) {
        event.preventDefault();
        var link = this;
        $.smoothScroll({
          scrollTarget: link.hash
        });
      });

      $('#change-speed').bind('click', function() {
        var $p1 = $('ul.mainnav a').first();
        var p1Opts = $p1.smoothScroll('options') || {};

        p1Opts.speed = p1Opts.speed === 1400 ? 400 : 1400;
        $p1.smoothScroll('options', p1Opts);
      });

      $('button.scrollsomething').bind('click', function(event) {
        event.preventDefault();
        $.smoothScroll({
          scrollElement: $('div.scrollme'),
          scrollTarget: '#findme'
        });
      });
      $('button.scrollhorz').bind('click', function(event) {
        event.preventDefault();
        $.smoothScroll({
          direction: 'left',
          scrollElement: $('div.scrollme'),
          scrollTarget: '.horiz'
        });

      });


      $('.page-scroll').on('click', function() {
        var wHeight = $(window).height();
        var wWidth = $(window).width();
        var rel = $(this).hasClass('down') ? '+=' : '-=';

        if (wWidth <= 560) {
          wHeight -= 130;
        }

        $.smoothScroll(rel + wHeight);
      });
    });
  </script>

</head>
<body>


  <div class="container">
    <div class="intro">
      <h1>jQuery smoothScroll Demo</h1>
      <p>View <a href="https://github.com/kswedberg/jquery-smooth-scroll">GitHub Repo</a></p>
      <p>Try it with <a href="bbq.html">jQuery BBQ hashchange support</a> or <a href="hashchange.html">native hashchange support (IE9+)</a></p>
      <p>Scroll the Document to one of the following paragraphs:</p>
    </div>

    <div class="demo-column">
      <ul class="mainnav">
        <li><a href="#p1">p1</a></li>
        <li><a href="#p2">p2</a></li>
        <li><a href="#p3">p3</a></li>
        <li><a href="#p4">p4</a></li>
        <li><a href="#p5">p5</a></li>
        <li><a href="#foo">link to nowhere</a></li>
        <li><a href="bbq.html#p3">bbq.html#p3</a></li>
      </ul>
      <p>Toggle scrolling speed for the p1 link <button id="change-speed">Change speed option</button></p>

      <div class="scrollme-wrapper">
        <p><strong>Scrollable div</strong></p>
        <div class="scrollme">
          <div class="scrollme-x">
            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
            </p>
            <div class="horiz">
              You found me by scrolling horizontally. nice job!
            </div>
          </div>
          <p>Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
          </p>
          <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
          </p>
          <p>Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
          </p>
          <div class="scrollme-x">
            <p id="findme">YOU FOUND ME!  Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
            </p>
            <div class="horiz">
              You scrolled horizontally <em>and</em> vertically. Awesome!
            </div>
          </div>
          <p>Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
          </p>
          <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
          </p>
          <p>Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
          </p>
        </div>

        <p><button class="scrollsomething">Scroll vertically</button></p>
        <p><button class="scrollhorz">Scroll horizontally</button></p>
      </div>
      <p id="p1">p1 Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
      <p id="p2">p2 Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
      <p id="p3">p3 Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
      <p id="p4">p4 Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
      <p id="p5">p5 Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
      <p id="p6">p6 Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
      <p class="subnav"><a href="#p1">p1</a> <a href="#p2">p2</a> <a href="#p3">p3</a> <a href="#p4">p4</a></p>
    </div>
  </div>
</body>
</html>
